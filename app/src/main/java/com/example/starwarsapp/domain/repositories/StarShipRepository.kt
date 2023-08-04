package com.example.starwarsapp.domain.repositories

import com.example.starwarsapp.data.local.StarWarsDatabase
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.StarshipsEntity
import javax.inject.Inject

data class StarshipInfo(
    val starship: StarshipsEntity,
    val films: List<FilmsEntity>,
    val pilots: List<PeopleEntity>
)

class StarShipRepository @Inject constructor(
    private val database: StarWarsDatabase
) {

    suspend fun getStarShips(starshipsId: String): StarshipInfo {
        val starships = database.starshipsDao().getStarshipsById(starshipsId)
        val films = database.filmsDao().getExtraFilms(starships.films)
        val characters = database.peopleDao().getExtraPeople(starships.pilots)

        return StarshipInfo(
            starships, films, characters
        )
    }
}