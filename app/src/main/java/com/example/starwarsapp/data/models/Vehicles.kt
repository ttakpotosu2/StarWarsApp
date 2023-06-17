package com.example.starwarsapp.data.models

import com.example.starwarsapp.domain.models.VehiclesEntity
import com.google.gson.annotations.SerializedName

data class Vehicles(
    @SerializedName("cargo_capacity") val cargoCapacity: String,
    val consumables: String,
    @SerializedName("cost_in_credits") val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val length: String,
    val manufacturer: String,
    @SerializedName("max_atmosphering_speed") val maxAtmospheringSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<String>,
    val url: String,
    @SerializedName("vehicle_class") val vehicleClass: String
) {
    fun toVehiclesEntity(): VehiclesEntity {
        return VehiclesEntity(
            cargoCapacity,
            consumables,
            costInCredits,
            created,
            crew,
            edited,
            films,
            length,
            manufacturer,
            maxAtmospheringSpeed,
            model,
            name,
            passengers,
            pilots,
            url,
            vehicleClass
        )
    }
}