package com.example.starwarsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "starships_table")
data class StarshipsEntity(
    val mglt: String,
    val cargoCapacity: String,
    val consumables: String,
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val hyperDriveRating: String,
    val length: String,
    val manufacturer: String,
    val maxAtmospheringSpeed: String,
    val model: String,
    @PrimaryKey(autoGenerate = false) val name: String,
    val passengers: String,
    val pilots: List<String>,
    val starshipClass: String,
    val url: String
)