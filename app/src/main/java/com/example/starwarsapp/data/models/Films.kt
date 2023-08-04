package com.example.starwarsapp.data.models

import com.example.starwarsapp.data.models.FilmsEntity
import com.google.gson.annotations.SerializedName

data class Films(
    val characters: List<String>,
    val created: String,
    val director: String,
    val edited: String,
    @SerializedName("episode_id") val episodeId: Int,
    @SerializedName("opening_crawl") val openingCrawl: String,
    val planets: List<String>,
    val producer: String,
    @SerializedName("release_date") val releaseDate: String,
    val species: List<String>,
    val starships: List<String>,
    val title: String,
    val url: String,
    val vehicles: List<String>
) {
    fun toFilmsEntity(): FilmsEntity {
        return FilmsEntity(
            characters,
            created,
            director,
            edited,
            episodeId,
            openingCrawl,
            planets,
            producer,
            releaseDate,
            species,
            starships,
            title,
            url,
            vehicles
        )
    }
}