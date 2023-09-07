package com.example.starwarsapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.starwarsapp.presentation.FilmsListCard
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.BackgroundGreen
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.FilmsViewModel

@Composable
fun FilmsScreen(
    toFilmDetailScreen: (String) -> Unit,
    viewModel: FilmsViewModel = hiltViewModel()
) {
    val films = viewModel.getFilms.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.background(BackgroundGreen).fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier.layoutModifiers()
        ) {
            Text(
                text = "Films",
                style = TextStyle(fontFamily = JetBrainsMono, fontSize = 44.sp, color = TextGreen),
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                Modifier.padding(16.dp)
            ) {
                items(
                    count = films.itemCount,
                    key = films.itemKey { it.title },
                    contentType = films.itemContentType()
                ){index ->
                    films[index]?.let {film ->
                        FilmsListCard(film = film) { toFilmDetailScreen(film.title) }
                    }
                }
            }
        }
    }
}