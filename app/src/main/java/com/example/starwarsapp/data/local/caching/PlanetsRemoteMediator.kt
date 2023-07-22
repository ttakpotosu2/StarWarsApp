package com.example.starwarsapp.data.local.caching

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.domain.models.PlanetsEntity
import com.example.starwarsapp.domain.models.PlanetsRemoteKeys
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

            val response = api.getPlanets(currentPage.toString())
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = response.results.map {
                    PlanetsRemoteKeys(
                        id = it.name,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                planetsRemoteKeysDao.addPlanetsRemoteKeys(remoteKeys = keys)
                planetsDao.addPlanets(planets = response.results.map { it.toPlanetsEntity()})
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
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

    private fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PlanetsEntity>
    ): PlanetsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { planetsRemoteKeysDao.getPlanetsRemoteKeys(id = it.name) }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, PlanetsEntity>
    ): PlanetsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { planetsRemoteKeysDao.getPlanetsRemoteKeys(id = it.name) }
    }
}