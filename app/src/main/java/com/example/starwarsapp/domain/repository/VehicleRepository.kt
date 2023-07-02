package com.example.starwarsapp.domain.repository

import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.PlanetsEntity
import com.example.starwarsapp.domain.models.SpeciesEntity
import com.example.starwarsapp.domain.models.StarshipsEntity
import com.example.starwarsapp.domain.models.VehiclesEntity
import javax.inject.Inject

data class VehicleInfo(
    val vehicle: VehiclesEntity,
    val films: List<FilmsEntity>,
    val pilots: List<PeopleEntity>
)

class VehicleRepository @Inject constructor(
    private val database: StarWarsDatabase
) {
    suspend fun getVehicles(vehicleId: String): VehicleInfo {
        val vehicles = database.vehiclesDao().getVehicleById(vehicleId)
        val films = database.filmsDao().getExtraFilms(vehicles.films)
        val characters = database.peopleDao().getExtraPeople(vehicles.pilots)

        return VehicleInfo(
            vehicles, films, characters
        )
    }
}