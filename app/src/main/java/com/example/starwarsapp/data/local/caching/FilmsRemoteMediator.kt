package com.example.starwarsapp.data.local.caching

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.FilmsRemoteKeys
import javax.inject.Inject

@ExperimentalPagingApi
class FilmsRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
): RemoteMediator<Int, FilmsEntity>() {

    private val filmsDao = database.filmsDao()
    private val filmsRemoteKeysDao = database.filmsRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmsEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.next?.minus(1) ?: 1
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.next
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
                else -> { 1 }
            }

            val response = api.getFilms(currentPage.toString())

            val getExtraPeople = api.getPeople(currentPage.toString())

            val peopleIds = response.results.flatMap { it.characters }.mapNotNull { Uri.parse(it).lastPathSegment }
            val peopleResponse = api.getMultiplePeople(peopleIds.joinToString (separator = ","))

            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            val remainingPeople = getExtraPeople.results - peopleResponse

            database.withTransaction {
                val keys = response.results.map {
                    FilmsRemoteKeys(
                        id = it.title,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                filmsRemoteKeysDao.addFilmsRemoteKeys(remoteKeys = keys)
                filmsDao.addFilms(films = response.results.map { it.toFilmsEntity()})

                database.peopleDao().addPeople(peopleResponse.map { it.toPeopleEntity() })

                database.peopleDao().addPeople(remainingPeople.map { it.toPeopleEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FilmsEntity>
    ): FilmsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                filmsRemoteKeysDao.getFilmsRemoteKeys(id = it.title)
            }
        }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, FilmsEntity>
    ): FilmsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { filmsRemoteKeysDao.getFilmsRemoteKeys(id = it.title) }
    }
}