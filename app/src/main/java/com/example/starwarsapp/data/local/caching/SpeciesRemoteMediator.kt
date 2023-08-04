package com.example.starwarsapp.data.local.caching

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.data.models.SpeciesEntity
import com.example.starwarsapp.data.models.SpeciesRemoteKeys
import javax.inject.Inject

@ExperimentalPagingApi
class SpeciesRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
): RemoteMediator<Int, SpeciesEntity>() {

    private val speciesDao = database.speciesDao()
    private val speciesRemoteKeysDao = database.speciesRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SpeciesEntity>
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

            val response = api.getSpecies(currentPage.toString())
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = response.results.map {
                    SpeciesRemoteKeys(
                        id = it.name,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                speciesRemoteKeysDao.addSpeciesRemoteKeys(remoteKeys = keys)
                speciesDao.addSpecies(species = response.results.map { it.toSpeciesEntity()})
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SpeciesEntity>
    ): SpeciesRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                speciesRemoteKeysDao.getSpeciesRemoteKeys(id = it.name)
            }
        }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, SpeciesEntity>
    ): SpeciesRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { speciesRemoteKeysDao.getSpeciesRemoteKeys(id = it.name) }
    }
}