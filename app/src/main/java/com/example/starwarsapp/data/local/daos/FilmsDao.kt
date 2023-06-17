package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.PeopleEntity

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films_table")
    suspend fun getFilms(): List<FilmsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilms(films: List<FilmsEntity>)

    @Query("DELETE FROM films_table")
    suspend fun deleteFilms()

    @Query("SELECT * FROM films_table WHERE title = :title ")
    suspend fun getFilmsById(title: String): FilmsEntity
}