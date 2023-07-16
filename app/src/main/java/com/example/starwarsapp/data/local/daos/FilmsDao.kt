package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.SpeciesEntity

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films_table")
    fun getFilms(): PagingSource<Int, FilmsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilms(films: List<FilmsEntity>)

    @Query("DELETE FROM films_table")
    suspend fun deleteFilms()

    @Query("SELECT * FROM films_table WHERE title = :title ")
    suspend fun getFilmsById(title: String): FilmsEntity

    @Query("SELECT * FROM films_table WHERE title IN (:name)")
    suspend fun getExtraFilms(name: List<String>): List<FilmsEntity>
}