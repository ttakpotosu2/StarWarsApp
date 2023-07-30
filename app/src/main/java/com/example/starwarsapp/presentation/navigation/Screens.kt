package com.example.starwarsapp.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "home_screen")
    object FilmsScreen : Screen(route = "films_screen")
    object PeopleScreen : Screen(route = "people_screen")
    object FilmDetailScreen : Screen(route = "film_detail_screen")
    object CharactersDetailScreen : Screen(route = "characters_detail_screen")
    object PlanetsDetailScreen : Screen(route = "planets_detail_screen")
    object StarShipsDetailScreen : Screen(route = "starships_detail_screen")
    object SpeciesDetailScreen : Screen(route = "species_detail_screen")
}