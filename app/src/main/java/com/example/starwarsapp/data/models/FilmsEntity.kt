package com.example.starwarsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films_table")
data class FilmsEntity(
    val characters: List<String>,
    val created: String,
    val director: String,
    val edited: String,
    val episodeId: Int,
    val openingCrawl: String,
    val planets: List<String>,
    val producer: String,
    val releaseDate: String,
    val species: List<String>,
    val starships: List<String>,
    @PrimaryKey(autoGenerate = false) val title: String,
    val url: String,
    val vehicles: List<String>
)