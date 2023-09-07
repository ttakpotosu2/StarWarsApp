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
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.data.models.StarshipsEntity
import com.example.starwarsapp.data.models.StarshipsRemoteKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class StarshipsRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
) : RemoteMediator<Int, StarshipsEntity>() {

    private val starshipsDao = database.starshipsDao()
    private val starshipsRemoteKeysDao = database.starshipsRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StarshipsEntity>
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
            val starshipsResponse = async { api.getStarships(currentPage.toString()) }

            val filmResult = filmResponse.await()
            val pilotResult = pilotsResponse.await()
            val starshipsResult = starshipsResponse.await()

            // Get Pilots of Starships
            val pilotId = starshipsResult.results
                .flatMap { it.pilots }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val pilotsList = mutableListOf<People>()
            // Get Films of Starships
            val filmId = starshipsResult.results
                .flatMap { it.films }
                .mapNotNull { Uri.parse(it).lastPathSegment }
            val filmsList = mutableListOf<Films>()

            val filmsDeferred = filmId.map { async { api.getFilm(it) } }
            val pilotsDeferred = pilotId.map { async { api.getPerson(it) } }

            filmsList.addAll(filmsDeferred.awaitAll())
            pilotsList.addAll(pilotsDeferred.awaitAll())

            val remainingFilms = filmResult.results - filmsList
            val remainingPilots = pilotResult.results - pilotsList

            val endOfPaginationReached = starshipsResult.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = starshipsResult.results.map {
                    StarshipsRemoteKeys(
                        id = it.name,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                starshipsRemoteKeysDao.addStarshipsRemoteKeys(remoteKeys = keys)
                starshipsDao.addStarships(starships = starshipsResult.results.map { it.toStarshipsEntity() })

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
        state: PagingState<Int, StarshipsEntity>
    ): StarshipsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                starshipsRemoteKeysDao.getStarshipsRemoteKeys(id = it.name)
            }
        }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, StarshipsEntity>
    ): StarshipsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { starshipsRemoteKeysDao.getStarshipsRemoteKeys(id = it.name) }
    }
}