package com.example.starwarsapp.data.models

import com.example.starwarsapp.domain.models.StarshipsEntity
import com.google.gson.annotations.SerializedName

data class Starships(
    @SerializedName("MGLT") val mglt: String,
    @SerializedName("cargo_capacity") val cargoCapacity: String,
    val consumables: String,
    @SerializedName("cost_in_credits") val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    @SerializedName("hyperdrive_rating") val hyperDriveRating: String,
    val length: String,
    val manufacturer: String,
    @SerializedName("max_atmosphering_speed") val maxAtmospheringSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<String>,
    @SerializedName("starship_class") val starshipClass: String,
    val url: String
) {
    fun toStarshipsEntity(): StarshipsEntity {
        return StarshipsEntity(
            mglt,
            cargoCapacity,
            consumables,
            costInCredits,
            created,
            crew,
            edited,
            films,
            hyperDriveRating,
            length,
            manufacturer,
            maxAtmospheringSpeed,
            model,
            name,
            passengers,
            pilots,
            starshipClass,
            url
        )
    }
}