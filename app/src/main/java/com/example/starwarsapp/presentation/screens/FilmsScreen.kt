package com.example.starwarsapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.starwarsapp.presentation.FilmsListCard
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.viewModels.FilmsViewModel
import com.example.starwarsapp.presentation.ui.theme.BackgroundGreen
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen

@Composable
fun FilmsScreen(
    toFilmDetailScreen: (String) -> Unit,
    viewModel: FilmsViewModel = hiltViewModel()
) {
    val films = viewModel.getFilms.collectAsLazyPagingItems()
    val sortedFilms = films.itemSnapshotList.sortedBy { it?.episodeId }

    Column(
        modifier = Modifier
            .background(BackgroundGreen)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier.layoutModifiers()
        ) {
            Text(
                text = "Films",
                style = TextStyle(
                    fontFamily = JetBrainsMono,
                    fontSize = 44.sp,
                    color = TextGreen
                ),
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                Modifier.padding(16.dp)
            ) {
                items(sortedFilms){films ->
                    if (films != null) {
                        FilmsListCard(
                            film = films,
                            onClick = { toFilmDetailScreen(films.title) }
                        )
                    }
                }
            }
        }
    }
}