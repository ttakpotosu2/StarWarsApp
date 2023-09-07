package com.example.starwarsapp.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "home_screen")
    object FilmsScreen : Screen(route = "films_screen")
    object FilmDetailScreen : Screen(route = "film_detail_screen")
    object PeopleScreen : Screen(route = "people_screen")
    object CharactersDetailScreen : Screen(route = "characters_detail_screen")
    object CharacterDetailScreen : Screen(route = "character_detail_screen")
    object PlanetsScreen : Screen(route = "planets_screen")
    object PlanetDetailScreen : Screen(route = "planet_detail_screen")
    object StarShipsScreen : Screen(route = "starships_screen")
    object StarShipDetailScreen : Screen(route = "starship_detail_screen")
    object SpeciesScreen : Screen(route = "species_screen")
    object SpecieDetailScreen : Screen(route = "specie_detail_screen")
    object VehiclesScreen : Screen(route = "vehicles_screen")
    object VehicleDetailScreen : Screen(route = "vehicle_detail_screen")

}