package com.example.starwarsapp.data.models

import com.example.starwarsapp.domain.models.PlanetsEntity
import com.google.gson.annotations.SerializedName

data class Planets(
    val climate: String,
    val created: String,
    val diameter: String,
    val edited: String,
    val films: List<String>,
    val gravity: String,
    val name: String,
    @SerializedName("orbital_period") val orbitalPeriod: String,
    val population: String,
    val residents: List<String>,
    @SerializedName("rotation_period") val rotationPeriod: String,
    @SerializedName("surface_water") val surfaceWater: String,
    val terrain: String,
    val url: String
) {
    fun toPlanetsEntity(): PlanetsEntity {
        return PlanetsEntity(
            climate,
            created,
            diameter,
            edited,
            films,
            gravity,
            name,
            orbitalPeriod,
            population,
            residents,
            rotationPeriod,
            surfaceWater,
            terrain,
            url
        )
    }
}