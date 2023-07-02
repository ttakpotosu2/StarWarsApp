package com.example.starwarsapp.domain.repository

import com.example.starwarsapp.data.local.StarWarsDatabase
import javax.inject.Inject

data class FilmInfo(
    val films
)

class FilmsRepository @Inject constructor(
    private val database: StarWarsDatabase
) {
}