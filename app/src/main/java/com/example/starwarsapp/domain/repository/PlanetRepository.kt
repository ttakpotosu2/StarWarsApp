package com.example.starwarsapp.domain.repository

import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.PlanetsEntity
import com.example.starwarsapp.domain.models.SpeciesEntity
import com.example.starwarsapp.domain.models.StarshipsEntity
import com.example.starwarsapp.domain.models.VehiclesEntity
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