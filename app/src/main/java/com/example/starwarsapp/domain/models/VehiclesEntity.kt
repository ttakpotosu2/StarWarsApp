package com.example.starwarsapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("vehicles_table")
data class VehiclesEntity(
    val cargoCapacity: String,
    val consumables: String,
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val length: String,
    val manufacturer: String,
    val maxAtmospheringSpeed: String,
    val model: String,
    @PrimaryKey(autoGenerate = false) val name: String,
    val passengers: String,
    val pilots: List<String>,
    val url: String,
    val vehicleClass: String
)