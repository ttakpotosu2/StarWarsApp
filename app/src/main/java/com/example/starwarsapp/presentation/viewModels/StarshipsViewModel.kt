package com.example.starwarsapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.local.caching.FilmsRemoteMediator
import com.example.starwarsapp.data.local.caching.StarshipsRemoteMediator
import com.example.starwarsapp.data.remote.StarWarsApi
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.StarshipsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class StarshipsViewModel @Inject constructor(
    api: StarWarsApi,
    private val database: StarWarsDatabase
): ViewModel() {
    private val pagingSourceFactory = { database.starshipsDao().getStarships() }

    @OptIn(ExperimentalPagingApi::class)
    val getFilms: Flow<PagingData<StarshipsEntity>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = StarshipsRemoteMediator(
            api, database
        ),
        pagingSourceFactory = pagingSourceFactory
    ).flow.flowOn(Dispatchers.IO)
}