package com.example.starwarsapp.data.local.caching

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.domain.models.VehiclesEntity
import com.example.starwarsapp.domain.models.VehiclesRemoteKeys
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

            val response = api.getVehicles(currentPage.toString())
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = response.map {
                    VehiclesRemoteKeys(
                        id = it.name,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                vehiclesRemoteKeysDao.addVehiclesRemoteKeys(remoteKeys = keys)
                vehiclesDao.addVehicle(vehicles = response.map { it.toVehiclesEntity()})
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
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

    private fun getRemoteKeyForFirstItem(
        state: PagingState<Int, VehiclesEntity>
    ): VehiclesRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { vehiclesRemoteKeysDao.getVehiclesRemoteKeys(id = it.name) }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, VehiclesEntity>
    ): VehiclesRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { vehiclesRemoteKeysDao.getVehiclesRemoteKeys(id = it.name) }
    }
}