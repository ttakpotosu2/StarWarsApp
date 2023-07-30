package com.example.starwarsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.starwarsapp.presentation.FilmCrawlDialog
import com.example.starwarsapp.presentation.FilmStates
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.navigation.Screen
import com.example.starwarsapp.presentation.viewModels.FilmViewModel
import com.example.starwarsapp.ui.theme.BackgroundGreen
import com.example.starwarsapp.ui.theme.JetBrainsMono
import com.example.starwarsapp.ui.theme.TextGreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmDetailScreen(
    navHostController: NavHostController,
    viewModel: FilmViewModel = hiltViewModel()
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val film = viewModel.film.value

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = CutCornerShape(bottomEnd = 50.dp),
                drawerContainerColor = BackgroundGreen,
                modifier = Modifier.border(
                    color = TextGreen,
                    width = 2.dp,
                    shape = CutCornerShape(bottomEnd = 50.dp)
                ),
                content = { DrawerContent(navHostController) }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGreen),
            topBar = { FilmsScreenTopBar(onclick = { TODO() }) },
        ) {
            when (film) {
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
                                modifier = Modifier.clickable {
                                    showCrawlDialog.value = true
                                }
                            )
                            if (showCrawlDialog.value){
                                FilmCrawlDialog(
                                    film = film.data.film,
                                    onDismiss = {showCrawlDialog.value = false}
                                )
                            }
                            Text(
                                text = "Characters",
                                style = style,
                                modifier = Modifier.clickable {
                                    navHostController.navigate(Screen.CharactersDetailScreen.route)
                                }
                            )
                            Text(
                                text = "Planets",
                                style = style,
                                modifier = Modifier.clickable {
                                    navHostController.navigate(Screen.PlanetsDetailScreen.route)
                                }
                            )
                            Text(
                                text = "Starships",
                                style = style,
                                modifier = Modifier.clickable {
                                    navHostController.navigate(Screen.StarShipsDetailScreen.route)
                                }
                            )
                            Text(
                                text = "Species",
                                style = style,
                                modifier = Modifier.clickable {
                                    navHostController.navigate(Screen.SpeciesDetailScreen.route)
                                }
                            )
                        }
                    }
                }
                is FilmStates.Loading -> CircularProgressIndicator(
                    color = TextGreen,
                    modifier = Modifier.size(150.dp)
                )
            }
        }
    }
}