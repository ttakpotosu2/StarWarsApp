package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.starwarsapp.data.models.PeopleEntity

@Dao
interface PeopleDao {

    @Query("SELECT * FROM people_table")
    fun getPeople(): PagingSource<Int, PeopleEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPeople(people: List<PeopleEntity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updatePeople(updateId: List<PeopleEntity>)

    @Query("DELETE FROM people_table")
    suspend fun deletePeople()

    @Query("SELECT * FROM people_table WHERE name = :name ")
    suspend fun getPeopleById(name: String): PeopleEntity

    @Query("SELECT * FROM people_table WHERE url IN (:urls)")
    suspend fun getExtraPeople(urls: List<String>): List<PeopleEntity>
}