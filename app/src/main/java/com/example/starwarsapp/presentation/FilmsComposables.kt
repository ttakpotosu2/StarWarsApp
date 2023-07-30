package com.example.starwarsapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.ui.theme.BackgroundGreen
import com.example.starwarsapp.ui.theme.JetBrainsMono
import com.example.starwarsapp.ui.theme.TextGreen

@Composable
fun FilmsListCard(
    film: FilmsEntity,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundGreen)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = film.title,
            style = TextStyle(
                fontFamily = JetBrainsMono,
                fontSize = 24.sp,
                color = TextGreen
            )
        )
    }
}

@Composable
fun FilmCrawlDialog(
    film: FilmsEntity,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .background(BackgroundGreen)
                .clip(CutCornerShape(bottomEnd = 50.dp))
                .fillMaxWidth()
                .border(
                    color = TextGreen,
                    width = 2.dp,
                    shape = CutCornerShape(bottomEnd = 50.dp)
                )
        ) {
            Text(
                text = film.openingCrawl,
                style = TextStyle(
                    fontFamily = JetBrainsMono,
                    fontSize = 20.sp,
                    color = TextGreen
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun FilmCharactersDialog(
    people: PeopleEntity,
    onDismiss: () -> Unit,
    onItemClick: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .height(400.dp)
                .clip(CutCornerShape(bottomEnd = 50.dp))
                .background(BackgroundGreen)
                .fillMaxWidth()
                .border(
                    color = TextGreen,
                    width = 2.dp,
                    shape = CutCornerShape(bottomEnd = 50.dp)
                )
        ) {
            Text(
                text = "Characters",
                style = TextStyle(
                    fontFamily = JetBrainsMono,
                    fontSize = 20.sp,
                    color = TextGreen
                ),
                modifier = Modifier.padding(8.dp)
            )
            LazyHorizontalStaggeredGrid(
                rows = StaggeredGridCells.Adaptive(100.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalItemSpacing = 4.dp,
                contentPadding = PaddingValues(8.dp)
            ) {

            }
        }
    }
}

@Composable
fun FilmCharactersItem(
    person: PeopleEntity?,
    onItemClick: () -> Unit
) {
    Box(modifier = Modifier
        .border(
            width = 1.dp,
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        )
        .clickable { onItemClick() }
    ){
        person?.name?.let {
            Text(
                text = it,
                style = TextStyle(
                    fontFamily = JetBrainsMono,
                    fontSize = 20.sp,
                    color = TextGreen
                )
            )
        }
    }
}