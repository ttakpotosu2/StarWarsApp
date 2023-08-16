package com.example.starwarsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.presentation.FilmCrawlDialog
import com.example.starwarsapp.presentation.FilmStates
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.FilmViewModel
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmDetailScreen(
    toPlanetDetailScreen: () -> Unit,
    toStarshipDetailScreen: () -> Unit,
    toCharacterDetailScreen: (String) -> Unit,
    toCharactersDetailScreen: () -> Unit,
    toSpeciesDetailScreen: () -> Unit,
    viewModel: FilmViewModel = hiltViewModel()
) {
    when (val film = viewModel.film.value) {
        is FilmStates.Success -> {
            Column(
                modifier = Modifier
                    .layoutModifiers()
                    .fillMaxSize()
            ) {
                val style = TextStyle(
                    fontFamily = JetBrainsMono,
                    fontSize = 20.sp,
                    color = TextGreen
                )
                Text(
                    text = film.data.film.title,
                    style = TextStyle(
                        fontFamily = JetBrainsMono,
                        fontSize = 44.sp,
                        color = TextGreen
                    ),
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
                Text(
                    text = "Episode ID: " + film.data.film.episodeId,
                    style = style,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    val showCrawlDialog = remember { mutableStateOf(false) }

                    Text(
                        text = "Directed by: " + film.data.film.director, style = style
                    )
                    Text(
                        text = "Produced by: " + film.data.film.producer, style = style
                    )
                    Text(
                        text = "Released: " + film.data.film.releaseDate, style = style
                    )
                    Text(
                        text = "Read opening crawl",
                        style = style,
                        modifier = Modifier.clickable { showCrawlDialog.value = true }
                    )
                    if (showCrawlDialog.value) {
                        FilmCrawlDialog(
                            film = film.data.film,
                            onDismiss = { showCrawlDialog.value = false }
                        )
                    }
                    Divider(thickness = 4.dp, color = TextGreen, modifier = Modifier.width(50.dp))
                }
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Characters:",
                        style = style,
                        modifier = Modifier.clickable { toCharactersDetailScreen() }
                    )
                    FlowRow(
                        maxItemsInEachRow = 2,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        film.data.characters.forEach {
                            CharactersDetailItem(person = it) {
                                toCharacterDetailScreen(it.name)
                            }
                        }
                    }
                    Text(
                        text = "Planets",
                        style = style,
                        modifier = Modifier.clickable { toPlanetDetailScreen() }
                    )
                    Text(
                        text = "Starships",
                        style = style,
                        modifier = Modifier.clickable { toStarshipDetailScreen() }
                    )
                    Text(
                        text = "Species",
                        style = style,
                        modifier = Modifier.clickable { toSpeciesDetailScreen() }
                    )
                }
            }
        }
        is FilmStates.Loading -> {
            CircularProgressIndicator(
                color = TextGreen,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Composable
fun CharactersDetailItem(
    person: PeopleEntity?,
    onItemClick: () -> Unit
) {
    val style = TextStyle(
        fontFamily = JetBrainsMono,
        fontSize = 20.sp,
        color = TextGreen
    )
    Column(
        modifier = Modifier
            .border(
                color = TextGreen,
                width = 2.dp,
                shape = CutCornerShape(bottomEnd = 10.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onItemClick() },
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        person?.name?.let {
            Text(text = "Name: $it",
                style = style,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}