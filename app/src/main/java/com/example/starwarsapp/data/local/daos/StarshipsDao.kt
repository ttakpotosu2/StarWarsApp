package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.SpeciesEntity
import com.example.starwarsapp.data.models.StarshipsEntity

@Dao
interface StarshipsDao {

    @Query("SELECT * FROM starships_table")
    fun getStarships(): PagingSource<Int, StarshipsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStarships(starships: List<StarshipsEntity>)

    @Query("DELETE FROM starships_table")
    suspend fun deleteStarships()

    @Query("SELECT * FROM starships_table WHERE name = :name ")
    suspend fun getStarshipsById(name: String): StarshipsEntity

    @Query("SELECT * FROM starships_table WHERE name IN (:name)")
    suspend fun getExtraStarships(name: List<String>): List<StarshipsEntity>
}