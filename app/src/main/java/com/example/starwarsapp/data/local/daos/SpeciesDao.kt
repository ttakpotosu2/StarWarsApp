package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.SpeciesEntity

@Dao
interface SpeciesDao {

    @Query("SELECT * FROM species_table")
    suspend fun getSpecies(): List<SpeciesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSpecies(species: List<SpeciesEntity>)

    @Query("DELETE FROM species_table")
    suspend fun deleteSpecies()

    @Query("SELECT * FROM species_table WHERE name = :name ")
    suspend fun getSpeciesById(name: String): SpeciesEntity
}