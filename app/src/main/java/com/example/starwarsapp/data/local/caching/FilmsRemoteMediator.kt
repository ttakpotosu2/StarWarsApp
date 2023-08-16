package com.example.starwarsapp.data.local.caching

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.FilmsRemoteKeys
import com.example.starwarsapp.data.remote.StarWarsApi
import javax.inject.Inject

@ExperimentalPagingApi
class FilmsRemoteMediator @Inject constructor(
    private val api: StarWarsApi,
    private val database: StarWarsDatabase
): RemoteMediator<Int, FilmsEntity>() {

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

//            1. Get ids
//            2. Create mutable list
//            3. Loop through the ids and call that endpoint that accepts :id
//            4. Get result from network call and append to the list
//            5. End loop

            val filmResponse = api.getFilms(currentPage.toString())

            val getPeopleId = filmResponse.results
                .flatMap { it.characters }
                .mapNotNull { Uri.parse(it).lastPathSegment }

            val ourList = mutableListOf<String>()

//          3. Loop through the ids and call that endpoint that accepts :id
            getPeopleId.forEach { id ->
                ourList.add(api.getMultiplePeople(id).toString())
            }
            val peopleResponse = api.getMultiplePeople(getPeopleId.toString())

            val endOfPaginationReached = filmResponse.results.isEmpty()
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                val keys = filmResponse.results.map {
                    FilmsRemoteKeys(
                        id = it.title,
                        prev = prevPage,
                        next = nextPage
                    )
                }
                database.filmsRemoteKeysDao().addFilmsRemoteKeys(remoteKeys = keys)
                database.filmsDao().addFilms(films = filmResponse.results.map { it.toFilmsEntity()})

                database.peopleDao().addPeople(peopleResponse.map { it.toPeopleEntity() })

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
                database.filmsRemoteKeysDao().getFilmsRemoteKeys(id = it.title)
            }
        }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, FilmsEntity>
    ): FilmsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { database.filmsRemoteKeysDao().getFilmsRemoteKeys(id = it.title) }
    }
}