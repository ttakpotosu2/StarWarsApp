package com.example.starwarsapp.data.local.caching

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.FilmsRemoteKeys
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
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prev
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.next
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = api.getFilms(currentPage.toString())
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = response.map {
                    FilmsRemoteKeys(
                        id = it.title,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                filmsRemoteKeysDao.addFilmsRemoteKeys(remoteKeys = keys)
                filmsDao.addFilms(films = response.map { it.toFilmsEntity()})
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

    private fun getRemoteKeyForFirstItem(
        state: PagingState<Int, FilmsEntity>
    ): FilmsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { filmsRemoteKeysDao.getFilmsRemoteKeys(id = it.title) }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, FilmsEntity>
    ): FilmsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { filmsRemoteKeysDao.getFilmsRemoteKeys(id = it.title) }
    }
}