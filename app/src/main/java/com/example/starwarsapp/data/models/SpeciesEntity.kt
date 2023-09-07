package com.example.starwarsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "species_table")
data class SpeciesEntity(
    val averageHeight: String,
    val averageLifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    val eyeColors: String,
    val films: List<String>,
    val hairColors: String,
    val homeWorld: String? = "not available",
    val language: String,
    @PrimaryKey(autoGenerate = false) val name: String,
    val people: List<String>,
    val skinColors: String,
    val url: String
)