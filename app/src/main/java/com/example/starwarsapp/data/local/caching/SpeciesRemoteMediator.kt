package com.example.starwarsapp.data.local.caching

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.models.Films
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Planets
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.data.models.SpeciesEntity
import com.example.starwarsapp.data.models.SpeciesRemoteKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class SpeciesRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
): RemoteMediator<Int, SpeciesEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SpeciesEntity>
    ): MediatorResult = withContext(Dispatchers.IO){
        return@withContext try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.next?.minus(1) ?: 1
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.next
                        ?: return@withContext MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
                else -> { 1 }
            }

            val speciesResponse = async { api.getSpecies(currentPage.toString()) }
            val charactersResponse = async { api.getPeople(currentPage.toString()) }
            val filmsResponse = async { api.getFilms(currentPage.toString()) }
            val homeWorldResponse = async { api.getPlanets(currentPage.toString()) }

            val speciesResult = speciesResponse.await()
            val charactersResult = charactersResponse.await()
            val filmResult = filmsResponse.await()
            val homeWorldResult = homeWorldResponse.await()


            //Get Home World of Species
            val planetId = speciesResult.results
                .map { it.homeWorld }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val homeWorldList = mutableListOf<Planets>()

            // Get People of Species
            val characterId = speciesResult.results
                .flatMap { it.people }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val peopleList = mutableListOf<People>()

            // Get Films of Species
            val filmId = speciesResult.results
                .flatMap { it.films }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val filmsList = mutableListOf<Films>()


            val filmsDeferred = filmId.map { async { api.getFilm(it) } }
            val planetsDeferred = planetId.map { async { api.getPlanet(it) } }
            val charactersDeferred = characterId.map { async { api.getPerson(it) } }

            filmsList.addAll(filmsDeferred.awaitAll())
            homeWorldList.addAll(planetsDeferred.awaitAll())
            peopleList.addAll(charactersDeferred.awaitAll())

            val remainingPlanets = homeWorldResult.results - homeWorldList
            val remainingFilms = filmResult.results - filmsList
            val remainingCharacters = charactersResult.results - peopleList

            val endOfPaginationReached = speciesResult.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = speciesResult.results.map {
                    SpeciesRemoteKeys(
                        id = it.name,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                database.speciesRemoteKeysDao().addSpeciesRemoteKeys(remoteKeys = keys)
                database.speciesDao().addSpecies(species = speciesResult.results.map { it.toSpeciesEntity()})

                database.planetsDao().addPlanets(homeWorldList.map { it.toPlanetsEntity() })
                database.planetsDao().updatePlanets(remainingPlanets.map { it.toPlanetsEntity() })

                database.filmsDao().addFilms(filmsList.map { it.toFilmsEntity() })
                database.filmsDao().updateFilms(remainingFilms.map { it.toFilmsEntity() })

                database.peopleDao().addPeople(peopleList.map { it.toPeopleEntity() })
                database.peopleDao().updatePeople(remainingCharacters.map { it.toPeopleEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SpeciesEntity>
    ): SpeciesRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                database.speciesRemoteKeysDao().getSpeciesRemoteKeys(id = it.name)
            }
        }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, SpeciesEntity>
    ): SpeciesRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { database.speciesRemoteKeysDao().getSpeciesRemoteKeys(id = it.name) }
    }
}