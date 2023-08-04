package com.example.starwarsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("planets_table")
data class PlanetsEntity(
    val climate: String,
    val created: String,
    val diameter: String,
    val edited: String,
    val films: List<String>,
    val gravity: String,
    @PrimaryKey(autoGenerate = false) val name: String,
    val orbitalPeriod: String,
    val population: String,
    val residents: List<String>,
    val rotationPeriod: String,
    val surfaceWater: String,
    val terrain: String,
    val url: String
)