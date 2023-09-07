package com.example.starwarsapp.presentation.screens

import android.annotation.SuppressLint
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
import com.example.starwarsapp.presentation.PersonItem
import com.example.starwarsapp.presentation.SpeciesStates
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.SpecieViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SpecieDetailScreen(
    viewModel: SpecieViewModel = hiltViewModel(),
    toPersonScreen: (String) -> Unit,
    toFilmDetailScreen: (String) -> Unit,
) {
    when (val specie = viewModel.specie.value) {
        is SpeciesStates.Success -> {
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
                    text = specie.data.specie.name,
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
                    val speciesInfo = specie.data.specie

                    Text(text = "Classification: " + speciesInfo.name, style = style)
                    Text(text = "Designation: " + speciesInfo.designation, style = style)
                    Text(text = "Average Height: " + speciesInfo.averageHeight, style = style)
                    Text(
                        text = "Average Lifespan: " + speciesInfo.averageLifespan + " years",
                        style = style
                    )
                    Text(text = "Language: " + speciesInfo.language, style = style)
                    Text(text = "Skin Colors: " + speciesInfo.skinColors, style = style)

                    Divider(thickness = 4.dp, color = TextGreen, modifier = Modifier.width(50.dp))
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (specie.data.homeWorld != null) {
                        Text(
                            text = "Home World: ",
                            style = style,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Box(
                                modifier = Modifier
                                    .border(
                                        color = TextGreen,
                                        width = 1.dp,
                                        shape = CutCornerShape(
                                            bottomEnd = 5.dp,
                                            topStart = 5.dp
                                        )
                                    )
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                specie.data.homeWorld.name.let {
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
                    }

                    //Films
                    val films = specie.data.films
                    val filmsSize = films.size
                    val filmsCount = if (filmsSize < 10) 1 else 3
                    val filmsHeight = if (filmsSize < 10) 30.dp else 120.dp
                    if (filmsSize >= 1) {
                        Text(
                            text = "Films", style = style,
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

                    // People
                    val people = specie.data.characters
                    val peopleSize = people.size
                    val peopleCount = if (peopleSize < 10) 1 else 3
                    val vehicleHeight = if (peopleSize < 10) 30.dp else 120.dp
                    if (peopleSize >= 1) {
                        Text(
                            text = "Vehicles:", style = style,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        LazyHorizontalStaggeredGrid(
                            rows = StaggeredGridCells.Fixed(peopleCount),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalItemSpacing = 8.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier.height(vehicleHeight)
                        ) {
                            items(people) { person ->
                                PersonItem(person = person) { toPersonScreen(person.name) }
                            }
                        }
                    }
                }

            }
        }

        SpeciesStates.Loading -> {
            CircularProgressIndicator(
                color = TextGreen,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}