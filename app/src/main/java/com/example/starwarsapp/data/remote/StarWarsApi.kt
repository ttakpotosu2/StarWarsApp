package com.example.starwarsapp.data.remote

import com.example.starwarsapp.data.models.Films
import com.example.starwarsapp.data.models.PagedResponse
import com.example.starwarsapp.data.models.People
import com.example.starwarsapp.data.models.Planets
import com.example.starwarsapp.data.models.Species
import com.example.starwarsapp.data.models.Starships
import com.example.starwarsapp.data.models.Vehicles
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApi {

    @GET("people/")
    suspend fun getPeople(@Query("page") page: String): PagedResponse<People>

    @GET("people/{id}/")
    suspend fun getPerson(@Path("id") id: String): People

    @GET("planets/")
    suspend fun getPlanets(@Query("page") page: String): PagedResponse<Planets>

    @GET("planets/{id}")
    suspend fun getPlanet(@Path("id") id: String): Planets

    @GET("films/")
    suspend fun getFilms(@Query("page") page: String): PagedResponse<Films>

    @GET("films/{id}")
    suspend fun getFilm(@Path("id") id: String): Films

    @GET("species/")
    suspend fun getSpecies(@Query("page") page: String): PagedResponse<Species>

    @GET("species/{id}")
    suspend fun getSpecie(@Path("id") id: String): Species

    @GET("vehicles/")
    suspend fun getVehicles(@Query("page") page: String): PagedResponse<Vehicles>

    @GET("vehicles/{id}")
    suspend fun getVehicle(@Path("id") id: String): Vehicles
    @GET("starships/")
    suspend fun getStarships(@Query("page") page: String): PagedResponse<Starships>

    @GET("starships/{id}")
    suspend fun getStarship(@Path("id") id: String): Starships
}