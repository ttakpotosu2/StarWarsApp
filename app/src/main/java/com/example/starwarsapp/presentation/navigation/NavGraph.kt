package com.example.starwarsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.starwarsapp.presentation.screens.FilmsScreen
import com.example.starwarsapp.presentation.screens.HomeScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screen.HomeScreen.route){
        composable(route = "home_screen"){
            HomeScreen(navHostController)
        }
        composable(route = Screen.FilmsScreen.route){
            FilmsScreen(navHostController)
        }
    }
}