package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.domain.models.PeopleEntity

@Dao
interface PeopleDao {

    @Query("SELECT * FROM people_table")
    suspend fun getPeople(): List<PeopleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPeople(people: List<PeopleEntity>)

    @Query("DELETE FROM people_table")
    suspend fun deletePeople()

    @Query("SELECT * FROM people_table WHERE name = :name ")
    suspend fun getPeopleById(name: String): PeopleEntity
}