package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.PlanetsEntity

@Dao
interface PlanetsDao {

    @Query("SELECT * FROM planets_table")
    suspend fun getPlanets(): List<PlanetsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlanets(planets: List<PlanetsEntity>)

    @Query("DELETE FROM planets_table")
    suspend fun deletePlanets()

    @Query("SELECT * FROM planets_table WHERE name = :name ")
    suspend fun getPlanetsById(name: String): PlanetsEntity
}