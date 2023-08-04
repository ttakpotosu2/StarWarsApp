package com.example.starwarsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.starwarsapp.presentation.FilmCrawlDialog
import com.example.starwarsapp.presentation.FilmStates
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.navigation.Screen
import com.example.starwarsapp.presentation.viewModels.FilmViewModel
import com.example.starwarsapp.presentation.viewModels.FilmsViewModel
import com.example.starwarsapp.ui.theme.BackgroundGreen
import com.example.starwarsapp.ui.theme.JetBrainsMono
import com.example.starwarsapp.ui.theme.TextGreen

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmDetailScreen(
   // navHostController: NavHostController,
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
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    val showCrawlDialog = remember { mutableStateOf(false) }

                    Text(
                        text = "Directed by: " + film.data.film.director,
                        style = style
                    )
                    Text(
                        text = "Produced by: " + film.data.film.producer,
                        style = style
                    )
                    Text(
                        text = "Released: " + film.data.film.releaseDate,
                        style = style
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
                    Divider(thickness = 2.dp, color = TextGreen)
                    val info = film.data.characters
                    Text(
                        text = "Characters:",
                        style = style,
                        modifier = Modifier.clickable { toCharactersDetailScreen() }
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        info.forEach { person ->
                            CharactersDetailCard(person = person) {
                                toCharacterDetailScreen(person.name)
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