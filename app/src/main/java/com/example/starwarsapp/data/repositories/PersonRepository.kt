package com.example.starwarsapp.data.repositories

import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.PlanetsEntity
import com.example.starwarsapp.data.models.SpeciesEntity
import com.example.starwarsapp.data.models.StarshipsEntity
import com.example.starwarsapp.data.models.VehiclesEntity
import javax.inject.Inject

data class PersonInfo(
    val character: PeopleEntity,
    val films: List<FilmsEntity>,
    val species: List<SpeciesEntity>,
    val starships: List<StarshipsEntity>,
    val vehicles: List<VehiclesEntity>,
    val homeWorld: PlanetsEntity?
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
        val homeWorld = person.homeWorld?.let { database.planetsDao().getHomeWorld(it) }
        return PersonInfo(
                person, films, species, starships, vehicles, homeWorld
            )
    }
}