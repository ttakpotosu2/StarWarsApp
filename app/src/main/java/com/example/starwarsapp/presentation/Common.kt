package com.example.starwarsapp.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.PlanetsEntity
import com.example.starwarsapp.data.models.SpeciesEntity
import com.example.starwarsapp.data.models.StarshipsEntity
import com.example.starwarsapp.data.models.VehiclesEntity
import com.example.starwarsapp.presentation.screens.style
import com.example.starwarsapp.presentation.ui.theme.TextGreen

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
                shape = CutCornerShape(bottomEnd = 5.dp, topStart = 5.dp)
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
                shape = CutCornerShape(bottomEnd = 5.dp, topStart = 5.dp)
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

@Composable
fun StarshipItem(
    starship: StarshipsEntity?,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                color = TextGreen,
                width = 1.dp,
                shape = CutCornerShape(bottomEnd = 5.dp, topStart = 5.dp)
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onItemClick() },
        contentAlignment = Alignment.Center
    ) {
        starship?.name?.let {
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
fun SpeciesItem(
    species: SpeciesEntity?,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                color = TextGreen,
                width = 1.dp,
                shape = CutCornerShape(bottomEnd = 5.dp, topStart = 5.dp)
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onItemClick() },
        contentAlignment = Alignment.Center
    ) {
        species?.name?.let {
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
fun VehicleItem(
    vehicles: VehiclesEntity?,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                color = TextGreen,
                width = 1.dp,
                shape = CutCornerShape(bottomEnd = 5.dp, topStart = 5.dp)
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onItemClick() },
        contentAlignment = Alignment.Center
    ) {
        vehicles?.name?.let {
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
fun FilmItem(
    film: FilmsEntity?,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                color = TextGreen,
                width = 1.dp,
                shape = CutCornerShape(bottomEnd = 5.dp, topStart = 5.dp)
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onItemClick() },
        contentAlignment = Alignment.Center
    ) {
        film?.title?.let {
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
fun HomeWorldItem(
    planet: PlanetsEntity?,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                color = TextGreen,
                width = 1.dp,
                shape = CutCornerShape(bottomEnd = 5.dp, topStart = 5.dp)
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