package com.example.starwarsapp.domain.repositories

import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.VehiclesEntity
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