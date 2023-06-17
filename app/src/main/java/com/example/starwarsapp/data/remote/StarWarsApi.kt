package com.example.starwarsapp.data.remote

import com.example.starwarsapp.data.models.Films
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Planets
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.models.Starships
import com.example.starwarsapp.data.models.Vehicles
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApi {

    @GET("people/")
    suspend fun getPeople(@Query("page") page: String): List<People>

    @GET("planets/")
    suspend fun getPlanets(@Query("page") page: String): List<Planets>

    @GET("films/")
    suspend fun getFilms(@Query("page") page: String): List<Films>

    @GET("species/")
    suspend fun getSpecies(@Query("page") page: String): List<Species>

    @GET("vehicles/")
    suspend fun getVehicles(@Query("page") page: String): List<Vehicles>

    @GET("starships/")
    suspend fun getStarships(@Query("page") page: String): List<Starships>

}
