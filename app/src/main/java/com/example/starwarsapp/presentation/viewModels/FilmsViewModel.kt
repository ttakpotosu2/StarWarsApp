package com.example.starwarsapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.local.caching.FilmsRemoteMediator
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.data.models.FilmsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    api: StarWarsApi,
    private val database: StarWarsDatabase
): ViewModel() {
    private val pagingSourceFactory = { database.filmsDao().getFilms() }

    @OptIn(ExperimentalPagingApi::class)
    val getFilms: Flow<PagingData<FilmsEntity>> = Pager(
        config = PagingConfig(pageSize = 1),
        remoteMediator = FilmsRemoteMediator(api, database),
        pagingSourceFactory = pagingSourceFactory
    ).flow.flowOn(Dispatchers.IO)
}//TODO:add a states