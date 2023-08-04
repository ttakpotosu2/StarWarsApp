package com.example.starwarsapp.domain.repositories

import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.PlanetsEntity
import javax.inject.Inject

data class PlanetInfo(
    val planets: PlanetsEntity,
    val characters: List<PeopleEntity>,
    val film: List<FilmsEntity>
)

class PlanetRepository @Inject constructor(
    private val database: StarWarsDatabase
) {

    suspend fun getPlanets(planetId: String): PlanetInfo {
        val planet = database.planetsDao().getPlanetsById(planetId)
        val films = database.filmsDao().getExtraFilms(planet.films)
        val characters = database.peopleDao().getExtraPeople(planet.residents)

        return PlanetInfo(
            planet, characters, films
        )
    }
}