package com.example.starwarsapp.presentation.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.starwarsapp.presentation.FilmCrawlDialog
import com.example.starwarsapp.presentation.FilmStates
import com.example.starwarsapp.presentation.PersonItem
import com.example.starwarsapp.presentation.PlanetItem
import com.example.starwarsapp.presentation.SpeciesItem
import com.example.starwarsapp.presentation.StarshipItem
import com.example.starwarsapp.presentation.VehicleItem
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.FilmViewModel

val style = TextStyle(
    fontFamily = JetBrainsMono,
    fontSize = 16.sp,
    color = TextGreen
)

@Composable
fun FilmDetailScreen(
    toPlanetDetailScreen: (String) -> Unit,
    toStarshipDetailScreen: (String) -> Unit,
    toCharacterDetailScreen: (String) -> Unit,
    toSpecieDetailScreen: (String) -> Unit,
    toVehicleDetailScreen: (String) -> Unit,
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

                    Text(text = "Directed by: " + film.data.film.director, style = style)
                    Text(text = "Produced by: " + film.data.film.producer, style = style)
                    Text(text = "Released: " + film.data.film.releaseDate, style = style)
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
                    val people = film.data.characters
                    val peopleSize = people.size
                    val peopleCount = if (peopleSize < 10) 1 else 3
                    val peopleHeight = if (peopleSize < 10) 30.dp else 120.dp
                    if (peopleSize >= 1) {
                        Text(
                            text = "Characters:",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = style
                        )
                        LazyHorizontalStaggeredGrid(
                            rows = StaggeredGridCells.Fixed(peopleCount),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalItemSpacing = 8.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier.height(peopleHeight)
                        ) {
                            items(film.data.characters) { person ->
                                PersonItem(person = person) { toCharacterDetailScreen(person.name) }
                            }
                        }
                    }
                    val planet = film.data.planets
                    val planetsSize = planet.size
                    val count = if (planetsSize < 10) 1 else 3
                    val height = if (planetsSize < 10) 30.dp else 120.dp
                    if (planetsSize >= 1) {
                        Text(
                            text = "Planets",
                            style = style,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                        LazyHorizontalStaggeredGrid(
                            rows = StaggeredGridCells.Fixed(count),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalItemSpacing = 8.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier.height(height)
                        ) {
                            items(planet) { planet ->
                                PlanetItem(planet = planet) { toPlanetDetailScreen(planet.name) }
                            }
                        }
                    }
                    val vehicle = film.data.vehicles
                    val vehiclesSize = vehicle.size
                    val vehicleCount = if (vehiclesSize < 10) 1 else 3
                    val vehicleHeight = if (vehiclesSize < 10) 30.dp else 120.dp
                    if (vehiclesSize >= 1) {
                        Text(
                            text = "Vehicles:",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = style
                        )
                        LazyHorizontalStaggeredGrid(
                            rows = StaggeredGridCells.Fixed(vehicleCount),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalItemSpacing = 8.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier.height(vehicleHeight)
                        ) {
                            items(vehicle) { vehicle ->
                                VehicleItem(vehicles = vehicle) { toVehicleDetailScreen(vehicle.name) }
                            }
                        }
                    }
                    val starship = film.data.starships
                    val starshipSize = starship.size
                    val starshipCount = if (starshipSize < 10) 1 else 3
                    val starshipHeight = if (starshipSize < 10) 30.dp else 120.dp
                    if (starshipSize >= 1) {
                        Text(
                            text = "Starships",
                            style = style,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                        LazyHorizontalStaggeredGrid(
                            rows = StaggeredGridCells.Fixed(starshipCount),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalItemSpacing = 8.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier.height(starshipHeight)
                        ) {
                            items(starship) { starship ->
                                StarshipItem(starship = starship) { toStarshipDetailScreen(starship.name) }
                            }
                        }
                        val species = film.data.species
                        val speciesSize = species.size
                        val speciesCount = if (speciesSize < 10) 1 else 3
                        val speciesHeight = if (speciesSize < 10) 30.dp else 120.dp
                        if (speciesSize >= 1) {
                            Text(
                                text = "Species",
                                style = style,
                                modifier = Modifier.padding(horizontal = 16.dp),
                            )
                            LazyHorizontalStaggeredGrid(
                                rows = StaggeredGridCells.Fixed(speciesCount),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalItemSpacing = 8.dp,
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                modifier = Modifier.height(speciesHeight)
                            ) {
                                items(species) { species ->
                                    SpeciesItem(species = species) { toSpecieDetailScreen(species.name) }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
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