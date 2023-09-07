package com.example.starwarsapp.data.local.caching

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.models.Films
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.PeopleRemoteKeys
import com.example.starwarsapp.data.models.Planets
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.models.Starships
import com.example.starwarsapp.data.models.Vehicles
import com.example.starwarsapp.data.remote.StarWarsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class PeopleRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
): RemoteMediator<Int, PeopleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PeopleEntity>
    ): MediatorResult = withContext(Dispatchers.IO) {

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

            val peopleResponse = async { api.getPeople(currentPage.toString()) }
            val filmsResponse = async { api.getFilms(currentPage.toString()) }
            val speciesResponse = async { api.getSpecies(currentPage.toString()) }
            val vehiclesResponse = async { api.getVehicles(currentPage.toString()) }
            val starshipsResponse = async { api.getStarships(currentPage.toString()) }
            val planetsResponse = async { api.getPlanets(currentPage.toString()) }

            val peopleResult = peopleResponse.await()
            val filmResult = filmsResponse.await()
            val speciesResult = speciesResponse.await()
            val vehicleResult = vehiclesResponse.await()
            val starshipsResult = starshipsResponse.await()
            val planetsResult = planetsResponse.await()


            // Get Films of Person
            val filmId = peopleResult.results
                .flatMap { it.films }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val filmsList = mutableListOf<Films>()

            // Get homePlanet
            val planetId = peopleResult.results
                .map { it.homeWorld }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val homeWorldList = mutableListOf<Planets>()

            // Get Species of Person
            val specieId = peopleResult.results
                .flatMap { it.species }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val speciesList = mutableListOf<Species>()

            // Get Vehicles of Person
            val vehicleId = peopleResult.results
                .flatMap { it.vehicles }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val vehicleList = mutableListOf<Vehicles>()

            // Get Starships of Person
            val starshipId = peopleResult.results
                .flatMap { it.starships }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val starshipsList = mutableListOf<Starships>()

            val filmsDeferred = filmId.map { async { api.getFilm(it) } }
            val planetsDeferred = planetId.map { async { api.getPlanet(it) } }
            val speciesDeferred = specieId.map { async { api.getSpecie(it) } }
            val vehiclesDeferred = vehicleId.map { async { api.getVehicle(it) } }
            val starshipsDeferred = starshipId.map { async { api.getStarship(it) } }

            filmsList.addAll(filmsDeferred.awaitAll())
            homeWorldList.addAll(planetsDeferred.awaitAll())
            speciesList.addAll(speciesDeferred.awaitAll())
            vehicleList.addAll(vehiclesDeferred.awaitAll())
            starshipsList.addAll(starshipsDeferred.awaitAll())

            // Paging
            val endOfPaginationReached = peopleResult.results.isEmpty()
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            val remainingFilms = filmResult.results - filmsList
            val remainingSpecies = speciesResult.results - speciesList
            val remainingVehicles = vehicleResult.results - vehicleList
            val remainingPlanets = planetsResult.results - homeWorldList
            val remainingStarships = starshipsResult.results - starshipsList

            database.withTransaction {
                val keys = peopleResult.results.map {
                    PeopleRemoteKeys(
                        id = it.name,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                database.peopleRemoteKeysDao().addPeopleRemoteKeys(remoteKeys = keys)
                database.peopleDao().addPeople(people = peopleResult.results.map { it.toPeopleEntity() })

                database.filmsDao().addFilms(filmsList.map { it.toFilmsEntity() })
                database.filmsDao().updateFilms(remainingFilms.map { it.toFilmsEntity() })

                database.speciesDao().addSpecies(speciesList.map { it.toSpeciesEntity() })
                database.speciesDao().updateSpecies(remainingSpecies.map { it.toSpeciesEntity() })

                database.vehiclesDao().addVehicles(vehicleList.map { it.toVehiclesEntity() })
                database.vehiclesDao().updateVehicles(remainingVehicles.map { it.toVehiclesEntity() })

                database.planetsDao().addPlanets(homeWorldList.map { it.toPlanetsEntity() })
                database.planetsDao().updatePlanets(remainingPlanets.map { it.toPlanetsEntity() })

                database.starshipsDao().addStarships(starshipsList.map { it.toStarshipsEntity() })
                database.starshipsDao().updateStarships(remainingStarships.map { it.toStarshipsEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PeopleEntity>
    ): PeopleRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                database.peopleRemoteKeysDao().getPeopleRemoteKeys(id = it.name)
            }
        }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, PeopleEntity>
    ): PeopleRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { database.peopleRemoteKeysDao().getPeopleRemoteKeys(id = it.name) }
    }
}