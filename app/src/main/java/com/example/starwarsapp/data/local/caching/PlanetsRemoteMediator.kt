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
import com.example.starwarsapp.data.models.PlanetsEntity
import com.example.starwarsapp.data.models.PlanetsRemoteKeys
import com.example.starwarsapp.data.remote.StarWarsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class PlanetsRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
): RemoteMediator<Int, PlanetsEntity>() {

    private val planetsDao = database.planetsDao()
    private val planetsRemoteKeysDao = database.planetsRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlanetsEntity>
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
            val planetsResponse = async { api.getPlanets(currentPage.toString()) }
            val peopleResponse = async { api.getPeople(currentPage.toString()) }
            val filmsResponse = async { api.getFilms(currentPage.toString()) }

            val planetsResult = planetsResponse.await()
            val peopleResult = peopleResponse.await()
            val filmResult = filmsResponse.await()

            // Get Films of Planet
            val filmId = planetsResult.results
                .flatMap { it.films }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val filmsList = mutableListOf<Films>()

            // Get People of Planet
            val personId = planetsResult.results
                .flatMap { it.residents }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val peopleList = mutableListOf<People>()

            val filmsDeferred = filmId.map { async { api.getFilm(it) } }
            val peopleDeferred = personId.map { async { api.getPerson(it) } }

            filmsList.addAll(filmsDeferred.awaitAll())
            peopleList.addAll(peopleDeferred.awaitAll())

            val remainingFilms = filmResult.results - filmsList
            val remainingResidents = peopleResult.results - peopleList

            val endOfPaginationReached = planetsResult.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = planetsResult.results.map {
                    PlanetsRemoteKeys(
                        id = it.name,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                planetsRemoteKeysDao.addPlanetsRemoteKeys(remoteKeys = keys)
                planetsDao.addPlanets(planets = planetsResult.results.map { it.toPlanetsEntity()})

                database.filmsDao().addFilms(filmsList.map { it.toFilmsEntity() })
                database.filmsDao().updateFilms(remainingFilms.map { it.toFilmsEntity() })

                database.peopleDao().addPeople(peopleList.map { it.toPeopleEntity() })
                database.peopleDao().updatePeople(remainingResidents.map { it.toPeopleEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PlanetsEntity>
    ): PlanetsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                planetsRemoteKeysDao.getPlanetsRemoteKeys(id = it.name)
            }
        }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, PlanetsEntity>
    ): PlanetsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { planetsRemoteKeysDao.getPlanetsRemoteKeys(id = it.name) }
    }
}