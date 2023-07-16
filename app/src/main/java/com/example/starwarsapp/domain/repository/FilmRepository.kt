package com.example.starwarsapp.domain.repository

import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.PlanetsEntity
import com.example.starwarsapp.domain.models.SpeciesEntity
import com.example.starwarsapp.domain.models.StarshipsEntity
import com.example.starwarsapp.domain.models.VehiclesEntity
import javax.inject.Inject

data class FilmInfo(
    val film: FilmsEntity,
    val species: List<SpeciesEntity>,
    val starships: List<StarshipsEntity>,
    val vehicles: List<VehiclesEntity>,
    val characters: List<PeopleEntity>,
    val planets: List<PlanetsEntity>
)

class FilmRepository @Inject constructor(
    private val database: StarWarsDatabase
) {

    suspend fun getFilms(filmsId: String): FilmInfo {
        val film = database.filmsDao().getFilmsById(filmsId)
        val species = database.speciesDao().getExtraSpecies(film.species)
        val starships = database.starshipsDao().getExtraStarships(film.starships)
        val vehicles = database.vehiclesDao().getExtraVehicles(film.vehicles)
        val characters = database.peopleDao().getExtraPeople(film.characters)
        val planets = database.planetsDao().getExtraPlanets(film.planets)

        return FilmInfo(
            film, species, starships, vehicles, characters, planets
        )
    }
}