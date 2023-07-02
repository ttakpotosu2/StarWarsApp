package com.example.starwarsapp.domain.repository

import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.SpeciesEntity
import com.example.starwarsapp.domain.models.StarshipsEntity
import com.example.starwarsapp.domain.models.VehiclesEntity
import javax.inject.Inject

data class PersonInfo(
    val character: PeopleEntity,
    val films: List<FilmsEntity>,
    val species: List<SpeciesEntity>,
    val starships: List<StarshipsEntity>,
    val vehicles: List<VehiclesEntity>
)

class PersonRepository @Inject constructor(
    private val database: StarWarsDatabase
) {

    suspend fun getPerson(personId: String): PersonInfo {
        val person = database.peopleDao().getPeopleById(personId)
        val films = database.filmsDao().getExtraFilms(person.films)
        val species = database.speciesDao().getExtraSpecies(person.species)
        val starships = database.starshipsDao().getExtraStarships(person.starships)
        val vehicles = database.vehiclesDao().getExtraVehicles(person.vehicles)

        return PersonInfo(
            person, films, species, starships, vehicles
        )
    }
}