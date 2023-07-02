package com.example.starwarsapp.domain.repository

import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.PlanetsEntity
import com.example.starwarsapp.domain.models.SpeciesEntity
import com.example.starwarsapp.domain.models.StarshipsEntity
import com.example.starwarsapp.domain.models.VehiclesEntity
import javax.inject.Inject

data class SpecieInfo(
    val specie: SpeciesEntity,
    val films: List<FilmsEntity>,
    val characters: List<PeopleEntity>
)

class SpecieRepository @Inject constructor(
    private val database: StarWarsDatabase
) {

    suspend fun getSpecies(specieId: String): SpecieInfo {
        val specie = database.speciesDao().getSpeciesById(specieId)
        val films = database.filmsDao().getExtraFilms(specie.films)
        val characters = database.peopleDao().getExtraPeople(specie.people)

        return SpecieInfo(
            specie, films, characters
        )
    }
}