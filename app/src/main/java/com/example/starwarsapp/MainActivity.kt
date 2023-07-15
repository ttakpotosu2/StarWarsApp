package com.example.starwarsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.starwarsapp.presentation.navigation.NavGraph
import com.example.starwarsapp.presentation.screens.FilmsScreen
import com.example.starwarsapp.ui.theme.BackgroundGreen
import com.example.starwarsapp.ui.theme.StarWarsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsAppTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavGraph(navHostController = navController)
            }
        }
    }
}