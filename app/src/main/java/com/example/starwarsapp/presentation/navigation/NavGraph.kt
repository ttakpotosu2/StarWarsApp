package com.example.starwarsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.starwarsapp.presentation.screens.CharacterDetailScreen
import com.example.starwarsapp.presentation.screens.CharactersScreen
import com.example.starwarsapp.presentation.screens.FilmDetailScreen
import com.example.starwarsapp.presentation.screens.FilmsScreen
import com.example.starwarsapp.presentation.screens.HomeScreen
import com.example.starwarsapp.presentation.screens.PlanetDetailScreen
import com.example.starwarsapp.presentation.screens.PlanetsScreen
import com.example.starwarsapp.presentation.screens.SpecieDetailScreen
import com.example.starwarsapp.presentation.screens.SpeciesScreen
import com.example.starwarsapp.presentation.screens.StarshipDetailScreen
import com.example.starwarsapp.presentation.screens.StarshipsScreen
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
                toFilmDetailScreen = { film ->
                    navHostController.navigate(Screen.FilmDetailScreen.route + "/${film}")
                }
            )
        }

        composable (route = Screen.FilmDetailScreen.route + "/{filmId}"){
            FilmDetailScreen(
                toPlanetDetailScreen = { id ->
                    navHostController.navigate(Screen.PlanetDetailScreen.route + "/${id}")
                },
                toStarshipDetailScreen = {id ->
                    navHostController.navigate(Screen.StarShipDetailScreen.route + "/${id}")
                },
                toCharacterDetailScreen = {id ->
                    navHostController.navigate(Screen.CharacterDetailScreen.route + "/${id}")
                },
                toSpecieDetailScreen = {id ->
                    navHostController.navigate(Screen.SpecieDetailScreen.route + "/${id}")
                },
                toVehicleDetailScreen = {id ->
                    navHostController.navigate(Screen.VehicleDetailScreen.route + "/${id}")
                }
            )
        }

//        composable (route = Screen.FilmDetailScreen.route) {
//            FilmDetailScreen(
//                toPlanetDetailScreen = {
//                    navHostController.navigate(Screen.PlanetsScreen.route)
//                },
//                toStarshipDetailScreen = {
//                    navHostController.navigate(Screen.StarShipsScreen.route)
//                },
//                toCharacterDetailScreen = { id ->
//                    navHostController.navigate(Screen.CharacterDetailScreen.route + "/${id}")
//                },
//                toSpecieDetailScreen = {
//                    navHostController.navigate(Screen.SpeciesScreen.route)
//                },
//                toVehicleDetailScreen = {
//                    navHostController.navigate(Screen.VehicleDetailScreen.route)
//                }
//            )
//        }

        //People
        composable (route = Screen.PeopleScreen.route) {
            CharactersScreen(
                toPersonScreen = { person ->
                    navHostController.navigate(Screen.CharacterDetailScreen.route + "/${person}")
                }
            )
        }
        composable(route = Screen.CharacterDetailScreen.route + "/{personId}") {
            CharacterDetailScreen(
                toSpecieDetailScreen = {id ->
                    navHostController.navigate(Screen.SpecieDetailScreen.route + "/${id}")
                },
                toVehicleDetailScreen = {id ->
                    navHostController.navigate(Screen.VehicleDetailScreen.route + "/${id}")
                },
                toStarshipDetailScreen = {id ->
                    navHostController.navigate(Screen.StarShipDetailScreen.route + "/${id}")
                },
                toFilmDetailScreen = {id ->
                    navHostController.navigate(Screen.FilmDetailScreen.route + "/${id}")
                },
//                toCharacterDetailScreen = { id ->
//                    navHostController.navigate(Screen.CharacterDetailScreen.route + "/${id}")
//                }
            )
        }
//
//        composable(route = Screen.CharactersDetailScreen.route) {
//            CharactersScreen(
//                toPersonScreen = { person ->
//                    navHostController.navigate(Screen.CharacterDetailScreen.route + "/${person}")
//                }
//            )
//        }
        //Planets
        composable(route = Screen.PlanetsScreen.route) {
            PlanetsScreen(
                toPlanetDetailScreen = {planet ->
                    navHostController.navigate(Screen.PlanetDetailScreen.route + "/${planet}")
                }
            )
        }
        composable (route = Screen.PlanetDetailScreen.route + "/{planetId}"){
            PlanetDetailScreen(
                toPersonScreen = {id ->
                    navHostController.navigate(Screen.CharacterDetailScreen.route + "/${id}")
                },
                toFilmDetailScreen = { id ->
                    navHostController.navigate(Screen.FilmDetailScreen.route + "/${id}")
                }
            )
        }

        //Starships
        composable(route = Screen.StarShipsScreen.route) {
            StarshipsScreen(
                toStarshipDetailScreen = {starship ->
                    navHostController.navigate(Screen.StarShipDetailScreen.route + "/${starship}")
                }
            )
        }
        composable(route = Screen.StarShipDetailScreen.route + "/{starshipId}"){
            StarshipDetailScreen(
                toPersonScreen = {id ->
                    navHostController.navigate(Screen.StarShipDetailScreen.route + "/${id}")
                },
                toFilmDetailScreen = {id ->
                    navHostController.navigate(Screen.FilmDetailScreen.route + "/${id}")
                }
            )
        }

        //Vehicles
        composable(route = Screen.VehiclesScreen.route) {
            VehiclesScreen(
                toVehicleDetailScreen = { vehicle ->
                    navHostController.navigate(Screen.VehicleDetailScreen.route + "/${vehicle}")
                }
            )
        }
        composable(route = Screen.VehicleDetailScreen.route + "/{vehicleId}") {
            VehicleDetailScreen(
                toPersonScreen = {id ->
                    navHostController.navigate(Screen.CharacterDetailScreen.route + "/${id}")
                },
                toFilmScreen = {id ->
                    navHostController.navigate(Screen.FilmDetailScreen.route + "/${id}")
                }
            )
        }
        //Species
        composable(route = Screen.SpeciesScreen.route){
            SpeciesScreen(
                toSpeciesDetailScreen = {specie ->
                    navHostController.navigate(Screen.SpecieDetailScreen.route + "/${specie}")
                }
            )
        }

        composable(route = Screen.SpecieDetailScreen.route + "/{specieId}"){
            SpecieDetailScreen(
                toPersonScreen = {id ->
                    navHostController.navigate(Screen.CharacterDetailScreen.route + "/${id}")
                },
                toFilmDetailScreen = {id ->
                    navHostController.navigate(Screen.FilmDetailScreen.route + "/${id}")
                }
            )
        }
    }
}