package com.example.starwarsapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.presentation.ui.theme.BackgroundGreen
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen

@Composable
fun PeopleListCard(
    people: PeopleEntity,
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
            text = "> " + people.name,
            style = TextStyle(
                fontFamily = JetBrainsMono,
                fontSize = 24.sp,
                color = TextGreen
            )
        )
    }
}