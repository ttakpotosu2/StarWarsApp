package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.SpeciesEntity

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films_table")
    fun getFilms(): PagingSource<Int, FilmsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilms(films: List<FilmsEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFilms(films: List<FilmsEntity>)

    @Query("DELETE FROM films_table")
    suspend fun deleteFilms()

    @Query("SELECT * FROM films_table WHERE title = :title ")
    suspend fun getFilmsById(title: String): FilmsEntity

    @Query("SELECT * FROM films_table WHERE url IN (:urls)")
    suspend fun getExtraFilms(urls: List<String>): List<FilmsEntity>
}