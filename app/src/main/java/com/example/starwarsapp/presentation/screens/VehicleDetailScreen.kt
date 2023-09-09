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
import com.example.starwarsapp.presentation.VehiclesStates
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.VehicleViewModel

@Composable
fun VehicleDetailScreen(
    viewModel: VehicleViewModel = hiltViewModel(),
    toFilmScreen: (String) -> Unit,
    toPersonScreen: (String) -> Unit
) {
    when (val vehicles = viewModel.vehicle.value) {
        is VehiclesStates.Success -> {
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
                    text = vehicles.data.vehicle.name,
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
                    Text(text = "Name: " + vehicles.data.vehicle.name, style = style)
                    Text(text = "Model: " + vehicles.data.vehicle.model, style = style)
                    Text(
                        text = "Manufacturer: " + vehicles.data.vehicle.manufacturer,
                        style = style
                    )
                    Text(text = "Length: " + vehicles.data.vehicle.length + "m", style = style)
                    Text(
                        text = "Max Speed: " + vehicles.data.vehicle.maxAtmospheringSpeed,
                        style = style
                    )
                    Text(text = "Crew: " + vehicles.data.vehicle.crew, style = style)
                    Text(
                        text = "Cargo Capacity: " + vehicles.data.vehicle.cargoCapacity,
                        style = style
                    )
                    Text(text = "Consumables: " + vehicles.data.vehicle.consumables, style = style)
                    Text(text = "Passengers: " + vehicles.data.vehicle.passengers, style = style)
                    Divider(thickness = 4.dp, color = TextGreen, modifier = Modifier.width(50.dp))
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val pilots = vehicles.data.pilots
                    val pilotsSize = pilots.size
                    val pilotCount = if (pilotsSize < 10) 1 else 3
                    val pilotsHeight = if (pilotsSize < 10) 30.dp else 120.dp
                    if (pilotsSize >= 1) {
                        Text(
                            text = "Pilots:",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = style
                        )
                        LazyHorizontalStaggeredGrid(
                            rows = StaggeredGridCells.Fixed(pilotCount),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalItemSpacing = 8.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier.height(pilotsHeight)
                        ) {
                            items(vehicles.data.pilots) { person ->
                                PersonItem(person = person) { toPersonScreen(person.name) }
                            }
                        }
                    }
                    //Films
                    val films = vehicles.data.films
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
                                FilmItem(film = film) { toFilmScreen(film.title) }
                            }
                        }
                    }
                }
            }
        }

        is VehiclesStates.Loading -> {
            CircularProgressIndicator(
                color = TextGreen,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}