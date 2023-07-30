package com.example.starwarsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.starwarsapp.presentation.screens.CharactersDetailScreen
import com.example.starwarsapp.presentation.screens.FilmDetailScreen
import com.example.starwarsapp.presentation.screens.FilmsScreen
import com.example.starwarsapp.presentation.screens.HomeScreen
import com.example.starwarsapp.presentation.screens.PeopleScreen
import com.example.starwarsapp.presentation.screens.PlanetsDetailScreen
import com.example.starwarsapp.presentation.screens.SpeciesDetailScreen
import com.example.starwarsapp.presentation.screens.StarshipsDetailScreen

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
        composable(route = Screen.CharactersDetailScreen.route){
            CharactersDetailScreen(navHostController = navHostController)
        }
        composable(route = Screen.PlanetsDetailScreen.route){
            PlanetsDetailScreen(navHostController = navHostController)
        }
        composable(route = Screen.StarShipsDetailScreen.route){
            StarshipsDetailScreen(navHostController = navHostController)
        }
        composable(route = Screen.SpeciesDetailScreen.route){
            SpeciesDetailScreen(navHostController = navHostController)
        }
    }
}