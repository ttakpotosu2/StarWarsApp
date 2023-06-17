package com.example.starwarsapp.data.local.caching

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.domain.models.StarshipsEntity
import com.example.starwarsapp.domain.models.StarshipsRemoteKeys
import javax.inject.Inject

@ExperimentalPagingApi
class StarshipsRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
): RemoteMediator<Int, StarshipsEntity>() {

    private val starshipsDao = database.starshipsDao()
    private val starshipsRemoteKeysDao = database.starshipsRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StarshipsEntity>
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

            val response = api.getStarships(currentPage.toString())
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = response.map {
                    StarshipsRemoteKeys(
                        id = it.name,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                starshipsRemoteKeysDao.addStarshipsRemoteKeys(remoteKeys = keys)
                starshipsDao.addStarships(starships = response.map { it.toStarshipsEntity()})
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
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

    private fun getRemoteKeyForFirstItem(
        state: PagingState<Int, StarshipsEntity>
    ): StarshipsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { starshipsRemoteKeysDao.getStarshipsRemoteKeys(id = it.name) }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, StarshipsEntity>
    ): StarshipsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { starshipsRemoteKeysDao.getStarshipsRemoteKeys(id = it.name) }
    }
}