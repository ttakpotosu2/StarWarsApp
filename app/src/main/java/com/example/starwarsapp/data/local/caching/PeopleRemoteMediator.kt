package com.example.starwarsapp.data.local.caching

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.PeopleRemoteKeys
import javax.inject.Inject

@ExperimentalPagingApi
class PeopleRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
): RemoteMediator<Int, PeopleEntity>() {

    private val peopleDao = database.peopleDao()
    private val peopleRemoteKeysDao = database.peopleRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PeopleEntity>
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
                else -> {
                    1
                }
            }

            val response = api.getPeople(currentPage.toString())
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = response.results.map {
                    PeopleRemoteKeys(
                        id = it.name,
                        prev = prevPage,
                        next = nextPage
                    )
                }

                peopleRemoteKeysDao.addPeopleRemoteKeys(remoteKeys = keys)
                peopleDao.addPeople(people = response.results.map { it.toPeopleEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PeopleEntity>
    ): PeopleRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                peopleRemoteKeysDao.getPeopleRemoteKeys(id = it.name)
            }
        }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, PeopleEntity>
    ): PeopleRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { peopleRemoteKeysDao.getPeopleRemoteKeys(id = it.name) }
    }
}