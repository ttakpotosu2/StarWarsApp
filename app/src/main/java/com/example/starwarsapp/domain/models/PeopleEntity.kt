package com.example.starwarsapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people_table")
data class PeopleEntity(
    val birthYear: String,
    val created: String,
    val edited: String,
    val eyeColor: String,
    val films: List<String>,
    val gender: String,
    val hairColor: String,
    val height: String,
    val homeWorld: String,
    val mass: String,
    @PrimaryKey(autoGenerate = false) val name: String,
    val skinColor: String,
    val species: List<String>,
    val starships: List<String>,
    val url: String,
    val vehicles: List<String>
)