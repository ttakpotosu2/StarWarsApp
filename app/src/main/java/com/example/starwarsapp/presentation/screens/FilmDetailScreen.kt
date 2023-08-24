package com.example.starwarsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.PlanetsEntity
import com.example.starwarsapp.presentation.FilmCrawlDialog
import com.example.starwarsapp.presentation.FilmStates
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.FilmViewModel

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmDetailScreen(
    toPlanetDetailScreen: () -> Unit,
    toStarshipDetailScreen: () -> Unit,
    toCharacterDetailScreen: (String) -> Unit,
    toSpeciesDetailScreen: () -> Unit,
    viewModel: FilmViewModel = hiltViewModel()
) {
    when (val film = viewModel.film.value) {
        is FilmStates.Success -> {
            val scroll = rememberScrollState()

            Column(
                modifier = Modifier
                    .layoutModifiers()
                    .fillMaxSize()
                    .verticalScroll(scroll)
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Characters:",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = style
                    )
                    LazyHorizontalStaggeredGrid(
                        rows = StaggeredGridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalItemSpacing = 8.dp,
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.height(120.dp)
                    ) {
                        items(film.data.characters) { person ->
                            PersonItem(person = person) {
                                toCharacterDetailScreen(person.name)
                            }
                        }
                    }
                    Text(
                        text = "Planets",
                        style = style,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                    LazyHorizontalStaggeredGrid(
                        rows = StaggeredGridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalItemSpacing = 8.dp,
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.height(120.dp)
                    ) {
                        items(film.data.planets) { planet ->
                            PlanetItem(planet = planet) {

                            }
                        }
                    }
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
                modifier = Modifier
                    .fillMaxSize()
                    .size(150.dp)
            )
        }
    }
}

val style = TextStyle(
    fontFamily = JetBrainsMono,
    fontSize = 16.sp,
    color = TextGreen
)

@Composable
fun PersonItem(
    person: PeopleEntity?,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                color = TextGreen,
                width = 1.dp,
                shape = CutCornerShape(bottomEnd = 5.dp)
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onItemClick() },
        contentAlignment = Alignment.Center
    ) {
        person?.name?.let {
            Text(
                text = it,
                style = style,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun PlanetItem(
    planet: PlanetsEntity?,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                color = TextGreen,
                width = 1.dp,
                shape = CutCornerShape(bottomEnd = 5.dp)
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onItemClick() },
        contentAlignment = Alignment.Center
    ) {
        planet?.name?.let {
            Text(
                text = it,
                style = style,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}