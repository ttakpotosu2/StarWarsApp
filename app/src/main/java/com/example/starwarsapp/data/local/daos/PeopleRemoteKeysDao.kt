package com.example.starwarsapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.models.PeopleRemoteKeys

@Dao
interface PeopleRemoteKeysDao {

    @Query("SELECT * FROM people_remote_keys_table WHERE id = :id")
    fun getPeopleRemoteKeys(id: String): PeopleRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPeopleRemoteKeys(remoteKeys: List<PeopleRemoteKeys>)

    @Query("DELETE FROM people_remote_keys_table")
    suspend fun deletePeopleRemoteKeys()
}