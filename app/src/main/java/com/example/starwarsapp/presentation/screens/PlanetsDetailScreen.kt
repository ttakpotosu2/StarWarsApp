package com.example.starwarsapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.starwarsapp.data.models.PlanetsEntity
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.BackgroundGreen
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.PlanetsViewModel

@Composable
fun PlanetsDetailScreen(
    viewModel: PlanetsViewModel = hiltViewModel()
) {
    val planets = viewModel.getPlanets.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .background(BackgroundGreen)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.layoutModifiers()
        ) {
            Text(
                text = "Planets",
                style = TextStyle(
                    fontFamily = JetBrainsMono,
                    fontSize = 44.sp,
                    color = TextGreen
                ),
                modifier = Modifier.padding(16.dp)
            )
            Divider(
                thickness = 4.dp,
                color = TextGreen,
                modifier = Modifier
                    .padding(16.dp)
                    .width(50.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyHorizontalStaggeredGrid(
                rows = StaggeredGridCells.Adaptive(100.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalItemSpacing = 8.dp,
                contentPadding = PaddingValues(8.dp)
            ) {
                items(
                    count = planets.itemCount,
                    key = planets.itemKey { it.name },
                    contentType = planets.itemContentType()
                ) { index ->
                    val data = planets[index]
                    data?.let {
                        PlanetsDetailCard(planets = it) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlanetsDetailCard(
    planets: PlanetsEntity, onItemClick: () -> Unit
) {
    val style = TextStyle(
        fontFamily = JetBrainsMono, fontSize = 20.sp, color = TextGreen
    )
    Column(
        modifier = Modifier
            .border(color = TextGreen, width = 2.dp, shape = CutCornerShape(bottomEnd = 10.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onItemClick() },
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Name: " + planets.name, style = style)
        Text(
            text = "Population: " + if (planets.population == "unknown") {
                planets.population
            } else {
                planets.population.toLong().addCommas()
            }, style = style
        )
        Text(text = "Terrain: " + planets.terrain, style = style)
    }
}

// Extension function to add commas to population
fun Long.addCommas(): String {
    return String.format("%,d", this)
}