package com.example.starwarsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.starwarsapp.presentation.PersonStates
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.navigation.Screen
import com.example.starwarsapp.presentation.viewModels.PersonViewModel
import com.example.starwarsapp.ui.theme.JetBrainsMono
import com.example.starwarsapp.ui.theme.TextGreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterDetailScreen(
    toStarShipsDetailScreen: () -> Unit,
    viewModel: PersonViewModel = hiltViewModel()
) {
    when (val person = viewModel.person.value) {
        is PersonStates.Success -> {
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
                    Text(text = "Skin Color: " + person.data.character.skinColor, style = style)
                    Text(text = "Height: " + person.data.character.height + "cm", style = style)

                    Divider(thickness = 2.dp, color = TextGreen)

                    Text(text = "Home World", style = style)
                    Text(text = "Films", style = style)
                    Text(text = "Species", style = style)
                    Text(
                        text = "Starships",
                        style = style,
                        modifier = Modifier.clickable { toStarShipsDetailScreen() }
                    )
                    Text(text = "Vehicles", style = style)
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