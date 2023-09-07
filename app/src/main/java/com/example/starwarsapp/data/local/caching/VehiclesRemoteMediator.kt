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
import com.example.starwarsapp.data.models.VehiclesEntity
import com.example.starwarsapp.data.models.VehiclesRemoteKeys
import com.example.starwarsapp.data.remote.StarWarsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class VehiclesRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
): RemoteMediator<Int, VehiclesEntity>() {

    private val vehiclesDao = database.vehiclesDao()
    private val vehiclesRemoteKeysDao = database.vehiclesRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, VehiclesEntity>
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

            val filmResponse = async { api.getFilms(currentPage.toString()) }
            val pilotsResponse = async { api.getPeople(currentPage.toString()) }
            val vehiclesResponse = async { api.getVehicles(currentPage.toString()) }

            val filmResult = filmResponse.await()
            val pilotResult = pilotsResponse.await()
            val vehiclesResult = vehiclesResponse.await()



            // Get Pilots of Vehicles
            val pilotId = vehiclesResult.results
                .flatMap { it.pilots }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val pilotsList = mutableListOf<People>()
            // Get Films of Vehicles
            val filmId = vehiclesResult.results
                .flatMap { it.films }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val filmsList = mutableListOf<Films>()

            val filmsDeferred = filmId.map { async { api.getFilm(it) } }
            val pilotsDeferred = pilotId.map { async { api.getPerson(it) } }

            filmsList.addAll(filmsDeferred.awaitAll())
            pilotsList.addAll(pilotsDeferred.awaitAll())

            val remainingFilms = filmResult.results - filmsList
            val remainingPilots = pilotResult.results - pilotsList
            val endOfPaginationReached = vehiclesResult.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = vehiclesResult.results.map {
                    VehiclesRemoteKeys(
                        id = it.name,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                vehiclesRemoteKeysDao.addVehiclesRemoteKeys(remoteKeys = keys)
                vehiclesDao.addVehicles(vehicles = vehiclesResult.results.map { it.toVehiclesEntity() })

                database.filmsDao().addFilms(filmsList.map { it.toFilmsEntity() })
                database.filmsDao().updateFilms(remainingFilms.map { it.toFilmsEntity() })

                database.peopleDao().addPeople(pilotsList.map { it.toPeopleEntity() })
                database.peopleDao().updatePeople(remainingPilots.map { it.toPeopleEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, VehiclesEntity>
    ): VehiclesRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                vehiclesRemoteKeysDao.getVehiclesRemoteKeys(id = it.name)
            }
        }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, VehiclesEntity>
    ): VehiclesRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { vehiclesRemoteKeysDao.getVehiclesRemoteKeys(id = it.name) }
    }
}