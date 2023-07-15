package com.example.starwarsapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.starwarsapp.ui.theme.BackgroundGreen
import com.example.starwarsapp.ui.theme.TextGreen

fun Modifier.layoutModifiers(): Modifier {
    return this.then(
        Modifier
            .background(BackgroundGreen)
            .padding(top = 86.dp)
            .padding(all = 22.dp)
            .clip(CutCornerShape(bottomEnd = 50.dp))
            .fillMaxWidth()
            .border(
                color = TextGreen,
                width = 2.dp,
                shape = CutCornerShape(bottomEnd = 50.dp)
            )
    )
}