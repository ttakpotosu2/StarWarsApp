package com.example.starwarsapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.models.PeopleRemoteKeys
import com.example.starwarsapp.data.models.SpeciesRemoteKeys

@Dao
interface SpeciesRemoteKeysDao {

    @Query("SELECT * FROM species_remote_keys_table WHERE id = :id")
    fun getSpeciesRemoteKeys(id: String): SpeciesRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSpeciesRemoteKeys(remoteKeys: List<SpeciesRemoteKeys>)

    @Query("DELETE FROM species_remote_keys_table")
    suspend fun deleteSpeciesRemoteKeys()
}