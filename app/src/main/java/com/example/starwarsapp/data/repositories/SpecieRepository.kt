package com.example.starwarsapp.data.repositories

import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.PlanetsEntity
import com.example.starwarsapp.data.models.SpeciesEntity
import javax.inject.Inject

data class SpecieInfo(
    val specie: SpeciesEntity,
    val films: List<FilmsEntity>,
    val characters: List<PeopleEntity>,
    val homeWorld: PlanetsEntity?
)

class SpecieRepository @Inject constructor(
    private val database: StarWarsDatabase
) {

    suspend fun getSpecies(specieId: String): SpecieInfo {
        val specie = database.speciesDao().getSpeciesById(specieId)
        val films = database.filmsDao().getExtraFilms(specie.films)
        val characters = database.peopleDao().getExtraPeople(specie.people)
        val homeWorld = specie.homeWorld?.let { database.planetsDao().getHomeWorld(it) }

        return SpecieInfo(
            specie, films, characters, homeWorld
        )
    }
}