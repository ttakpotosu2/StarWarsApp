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
import com.example.starwarsapp.presentation.StarshipsStates
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.StarshipViewModel

@Composable
fun StarshipDetailScreen(
    viewModel: StarshipViewModel = hiltViewModel(),
    toPersonScreen: (String) -> Unit,
    toFilmDetailScreen: (String) -> Unit,
) {

    when (val starship = viewModel.sratship.value) {
        is StarshipsStates.Loading -> {

            CircularProgressIndicator(
                color = TextGreen,
                modifier = Modifier
                    .fillMaxSize()
                    .size(150.dp)
            )
        }

        is StarshipsStates.Success -> {
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
                    text = starship.data.starship.name,
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
                    Text(text = "Model: " + starship.data.starship.model, style = style)
                    Text(
                        text = "Starship Class: " + starship.data.starship.starshipClass,
                        style = style
                    )
                    Text(
                        text = "Manufacturer: " + starship.data.starship.manufacturer,
                        style = style
                    )
                    Text(
                        text = "Cost: " + if (starship.data.starship.costInCredits == "unknown"){
                            starship.data.starship.costInCredits
                        } else {
                            starship.data.starship.costInCredits.toLong().addCommas() + " Credits"
                        },
                        style = style
                    )
                    Text(text = "Length: " + starship.data.starship.length + "m", style = style)
                    Text(text = "Crew: " + starship.data.starship.crew, style = style)
                    Text(text = "Passengers: " + starship.data.starship.passengers, style = style)
                    Text(
                        text = "Max Atmospheric Screen: " + starship.data.starship.maxAtmospheringSpeed,
                        style = style
                    )
                    Text(
                        text = "Hyper Drive Rating: " + starship.data.starship.hyperDriveRating,
                        style = style
                    )
                    Text(
                        text = "Cargo Capacity: " + if(starship.data.starship.cargoCapacity == "unknown"){
                            starship.data.starship.cargoCapacity
                        } else {
                            starship.data.starship.cargoCapacity.toLong().addCommas()
                        },
                        style = style
                    )

                    Divider(thickness = 4.dp, color = TextGreen, modifier = Modifier.width(50.dp))
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    //Pilots
                    val pilots = starship.data.pilots
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
                            items(starship.data.pilots) { person ->
                                PersonItem(person = person) { toPersonScreen(person.name) }
                            }
                        }
                    }
                    //Films
                    val films = starship.data.films
                    val filmsSize = films.size
                    val filmsCount = if (filmsSize < 10) 1 else 3
                    val filmsHeight = if (filmsSize < 10) 30.dp else 120.dp
                    if (filmsSize >= 1) {
                        Text(
                            text = "Films",
                            style = style,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
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
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}