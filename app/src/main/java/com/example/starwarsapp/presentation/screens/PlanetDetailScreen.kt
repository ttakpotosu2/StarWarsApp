package com.example.starwarsapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.starwarsapp.presentation.FilmItem
import com.example.starwarsapp.presentation.PersonItem
import com.example.starwarsapp.presentation.PlanetStates
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.PlanetViewModel

@Composable
fun PlanetDetailScreen(
    viewModel: PlanetViewModel = hiltViewModel(),
    toPersonScreen: (String) -> Unit,
    toFilmDetailScreen: (String) -> Unit,
) {

    when (val planet = viewModel.planet.value) {
        is PlanetStates.Loading -> {
            CircularProgressIndicator(
                color = TextGreen,
                modifier = Modifier.size(150.dp)
            )
        }

        is PlanetStates.Success -> {
            Column(
                modifier = Modifier
                    .layoutModifiers()
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val style = TextStyle(
                    fontFamily = JetBrainsMono,
                    fontSize = 20.sp,
                    color = TextGreen
                )
                Text(
                    text = planet.data.planets.name,
                    style = TextStyle(
                        fontFamily = JetBrainsMono,
                        fontSize = 44.sp,
                        color = TextGreen
                    ),
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "Diameter: " + planet.data.planets.diameter.toLong().addCommas() + "km", style = style)
                    Text(
                        text = "Rotation Period: " + planet.data.planets.rotationPeriod + "hours on it's axis",
                        style = style
                    )
                    Text(
                        text = "Orbital Period: " + planet.data.planets.orbitalPeriod + "days around local star",
                        style = style
                    )
                    Text(text = "Gravity: " + planet.data.planets.gravity + "Gs", style = style)
                    Text(
                        text = "Population: " + if (planet.data.planets.population == "unknown") {
                            planet.data.planets.population
                        } else {
                            planet.data.planets.population.toLong().addCommas()
                        }, style = style
                    )
                    Text(text = "Climate: " + planet.data.planets.climate, style = style)
                    Text(text = "Terrain: " + planet.data.planets.climate, style = style)

                    Divider(thickness = 4.dp, color = TextGreen, modifier = Modifier.width(50.dp))
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    val people = planet.data.characters
                    val peopleSize = people.size
                    val peopleCount = if (peopleSize < 10) 1 else 3
                    val peopleHeight = if (peopleSize < 10) 30.dp else 120.dp
                    if (peopleSize >= 1) {
                        Text(text = "Residents:", style = style, modifier = Modifier.padding(horizontal = 16.dp))
                        LazyHorizontalStaggeredGrid(
                            rows = StaggeredGridCells.Fixed(peopleCount),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalItemSpacing = 8.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier.height(peopleHeight)
                        ) {
                            items(planet.data.characters) { character ->
                                PersonItem(person = character) { toPersonScreen(character.name) }
                            }
                        }
                    }
                    //Films
                    val films = planet.data.film
                    val filmsSize = films.size
                    val filmsCount = if (filmsSize < 10) 1 else 3
                    val filmsHeight = if (filmsSize < 10) 30.dp else 120.dp
                    if (filmsSize >= 1) {
                        Text(text = "Films", style = style, modifier = Modifier.padding(horizontal = 16.dp))
                        LazyHorizontalStaggeredGrid(
                            rows = StaggeredGridCells.Fixed(filmsCount),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalItemSpacing = 8.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier.height(filmsHeight)
                        ) {
                            items(films) { film ->
                                FilmItem(film = film) { toFilmDetailScreen(film.title) }
                            }
                        }
                    }
                }
            }
        }
    }
}