package com.example.starwarsapp.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.starwarsapp.presentation.screens.FilmDetailScreen
import com.example.starwarsapp.presentation.screens.FilmsScreen
import com.example.starwarsapp.presentation.screens.HomeScreen
import com.example.starwarsapp.presentation.screens.PeopleScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screen.HomeScreen.route){

        composable(
            route = "home_screen"
        ){
            HomeScreen(navHostController)
        }
        composable(route = Screen.FilmsScreen.route){
            FilmsScreen(navHostController = navHostController)
        }
        composable(route = Screen.FilmDetailScreen.route + "/{filmId}"){
            FilmDetailScreen(navHostController)
        }
        composable(route = Screen.PeopleScreen.route){
            PeopleScreen(navHostController = navHostController)
        }
    }
}