package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.models.PlanetsEntity

@Dao
interface PlanetsDao {

    @Query("SELECT * FROM planets_table")
    fun getPlanets(): PagingSource<Int, PlanetsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlanets(planets: List<PlanetsEntity>)

    @Query("DELETE FROM planets_table")
    suspend fun deletePlanets()

    @Query("SELECT * FROM planets_table WHERE name = :name ")
    suspend fun getPlanetsById(name: String): PlanetsEntity

    @Query("SELECT * FROM planets_table WHERE name IN (:name)")
    suspend fun getExtraPlanets(name: List<String>): List<PlanetsEntity>
}