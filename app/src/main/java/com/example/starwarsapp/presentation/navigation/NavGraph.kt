package com.example.starwarsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.starwarsapp.presentation.screens.PersonScreen
import com.example.starwarsapp.presentation.screens.PeopleDetailScreen
import com.example.starwarsapp.presentation.screens.FilmDetailScreen
import com.example.starwarsapp.presentation.screens.FilmsScreen
import com.example.starwarsapp.presentation.screens.HomeScreen
import com.example.starwarsapp.presentation.screens.PeopleScreen
import com.example.starwarsapp.presentation.screens.PlanetsDetailScreen
import com.example.starwarsapp.presentation.screens.SpeciesScreen
import com.example.starwarsapp.presentation.screens.StarshipsDetailScreen
import com.example.starwarsapp.presentation.screens.VehicleDetailScreen
import com.example.starwarsapp.presentation.screens.VehiclesScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) { HomeScreen() }

        //Films
        composable(route = Screen.FilmsScreen.route) {
            FilmsScreen(
                toFilmDetailScreen = { id ->
                    navHostController.navigate(Screen.FilmDetailScreen.route + "/${id}")
                }
            )
        }
        composable(route = Screen.FilmDetailScreen.route + "/{filmId}") {
            FilmDetailScreen(
                toPlanetDetailScreen = {
                    navHostController.navigate(Screen.PlanetsScreen.route)
                },
                toStarshipDetailScreen = {
                    navHostController.navigate(Screen.StarShipsScreen.route)
                },
                toCharacterDetailScreen = { id ->
                    navHostController.navigate(Screen.PersonScreen.route + "/${id}")
                },
                toSpeciesDetailScreen = {
                    navHostController.navigate(Screen.SpeciesScreen.route)
                }
            )
        }
        //People
        composable(route = Screen.PeopleScreen.route) {
            PeopleScreen(
                toCharacterDetailScreen = { person ->
                    navHostController.navigate(Screen.PersonScreen.route + "/${person}")
                }
            )
        }
        composable(route = Screen.PersonScreen.route + "/{personId}") {
            PersonScreen()
        }
        composable(route = Screen.PeopleDetailScreen.route) {
            PeopleDetailScreen(
                toCharacterDetailScreen = { person ->
                    navHostController.navigate(Screen.PersonScreen.route + "/${person}")
                }
            )
        }
        //Planets
        composable(route = Screen.PlanetsScreen.route) {
            PlanetsDetailScreen()
        }
        composable(route = Screen.StarShipsScreen.route) {
            StarshipsDetailScreen()
        }
        //Vehicles
        composable(route = Screen.VehiclesScreen.route + "/{vehicleId}") {
            VehiclesScreen(
                toVehicleDetailScreen = { vehicle ->
                    navHostController.navigate(Screen.VehicleDetailScreen.route + "/${vehicle}")
                }
            )
        }
        composable(route = Screen.VehicleDetailScreen.route) {
            VehicleDetailScreen()
        }
        //Species
        composable(route = Screen.SpeciesScreen.route){
            SpeciesScreen()
        }
    }
}