package com.example.starwarsapp.presentation.navigation

data class NavItems(
    val label: String,
    val route: String
)

val navScreenItems = listOf(
    NavItems(
        label = "Films",
        route = Screen.FilmsScreen.route
    ),
    NavItems(
        label = "People",
        route = Screen.PeopleScreen.route
    ),
    NavItems(
        label = "Planets",
        route = Screen.PlanetsDetailScreen.route
    ),
    NavItems(
        label = "Species",
        route = Screen.SpeciesDetailScreen.route
    ),
    NavItems(
        label = "Vehicles",
        route = Screen.VehiclesDetailScreen.route
    ),
    NavItems(
        label = "Starships",
        route = Screen.StarShipsDetailScreen.route
    ),
)