package com.example.starwarsapp.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "home_screen")
    object FilmsScreen : Screen(route = "films_screen")
}