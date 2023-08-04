package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.SpeciesEntity

@Dao
interface SpeciesDao {

    @Query("SELECT * FROM species_table")
    fun getSpecies(): PagingSource<Int, SpeciesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSpecies(species: List<SpeciesEntity>)

    @Query("DELETE FROM species_table")
    suspend fun deleteSpecies()

    @Query("SELECT * FROM species_table WHERE name = :name ")
    suspend fun getSpeciesById(name: String): SpeciesEntity

    @Query("SELECT * FROM species_table WHERE name IN (:name)")
    suspend fun getExtraSpecies(name: List<String>): List<SpeciesEntity>
}