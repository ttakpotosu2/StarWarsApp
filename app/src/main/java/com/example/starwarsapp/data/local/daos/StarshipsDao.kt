package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.StarshipsEntity

@Dao
interface StarshipsDao {

    @Query("SELECT * FROM starships_table")
    suspend fun getStarships(): List<StarshipsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStarships(starships: List<StarshipsEntity>)

    @Query("DELETE FROM starships_table")
    suspend fun deleteStarships()

    @Query("SELECT * FROM starships_table WHERE name = :name ")
    suspend fun getStarshipsById(name: String): StarshipsEntity
}