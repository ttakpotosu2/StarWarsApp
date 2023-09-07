package com.example.starwarsapp.presentation.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.starwarsapp.presentation.FilmItem
import com.example.starwarsapp.presentation.PersonStates
import com.example.starwarsapp.presentation.SpeciesItem
import com.example.starwarsapp.presentation.StarshipItem
import com.example.starwarsapp.presentation.VehicleItem
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.PersonViewModel

@Composable
fun CharacterDetailScreen(
    viewModel: PersonViewModel = hiltViewModel(),
    toSpecieDetailScreen: (String) -> Unit,
    toVehicleDetailScreen: (String) -> Unit,
    toStarshipDetailScreen: (String) -> Unit,
    toFilmDetailScreen: (String) -> Unit,
    //toCharacterDetailScreen: (String) -> Unit,
) {
    when (val person = viewModel.person.value) {
        is PersonStates.Success -> {
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
                    text = person.data.character.name,
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
                    Text(text = "Gender: " + person.data.character.gender, style = style)
                    Text(text = "Birth Year: " + person.data.character.birthYear, style = style)
                    Text(text = "Eye Color: " + person.data.character.eyeColor, style = style)
                    Text(text = "Hair Color: " + person.data.character.hairColor, style = style)
                    Text(text = "Mass: " + person.data.character.mass + "kg", style = style)
                    Text(text = "Skin Color: " + person.data.character.skinColor, style = style)
                    Text(text = "Height: " + person.data.character.height + "cm", style = style)

                    Divider(thickness = 4.dp, color = TextGreen, modifier = Modifier.width(50.dp))
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Home World
                    Text(text = "Home World ", style = style, modifier = Modifier.padding(horizontal = 16.dp))
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Box(
                            modifier = Modifier
                                .border(
                                    color = TextGreen,
                                    width = 1.dp,
                                    shape = CutCornerShape(bottomEnd = 5.dp, topStart = 5.dp)
                                )
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                .clickable {},
                            contentAlignment = Alignment.Center
                        ) {
                            person.data.homeWorld?.name?.let {
                                Text(
                                    text = it,
                                    style = TextStyle(
                                        fontFamily = JetBrainsMono,
                                        fontSize = 16.sp,
                                        color = TextGreen
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    //Films
                    val films = person.data.films
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
                                FilmItem(film = film) {
                                    toFilmDetailScreen(film.title)
                                }
                            }
                        }
                    }
                    //Species
                    val species = person.data.species
                    val speciesSize = species.size
                    val speciesCount = if (speciesSize < 10) 1 else 3
                    val speciesHeight = if (speciesSize < 10) 30.dp else 120.dp
                    if (speciesSize >= 1) {
                        Text(text = "Species", style = style, modifier = Modifier.padding(horizontal = 16.dp))
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
                    // Starships
                    val starship = person.data.starships
                    val starshipSize = starship.size
                    val starshipCount = if (starshipSize < 10) 1 else 3
                    val starshipHeight = if (starshipSize < 10) 30.dp else 120.dp
                    if (starshipSize >= 1) {
                        Text(text = "Starships", style = style, modifier = Modifier.padding(horizontal = 16.dp))
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
                    }
                    // Vehicles
                    val vehicle = person.data.vehicles
                    val vehiclesSize = vehicle.size
                    val vehicleCount = if (vehiclesSize < 10) 1 else 3
                    val vehicleHeight = if (vehiclesSize < 10) 30.dp else 120.dp
                    if (speciesSize >= 1) {
                        Text(text = "Vehicles:", style = style, modifier = Modifier.padding(horizontal = 16.dp))
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
                }
            }
        }

        PersonStates.Loading -> {
            CircularProgressIndicator(
                color = TextGreen,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}