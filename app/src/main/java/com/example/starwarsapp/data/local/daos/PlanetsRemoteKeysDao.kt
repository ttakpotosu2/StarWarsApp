package com.example.starwarsapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.domain.models.PlanetsRemoteKeys

@Dao
interface PlanetsRemoteKeysDao {

    @Query("SELECT * FROM planets_remote_keys_table WHERE id = :id")
    fun getPlanetsRemoteKeys(id: String): PlanetsRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlanetsRemoteKeys(remoteKeys: List<PlanetsRemoteKeys>)

    @Query("DELETE FROM planets_remote_keys_table")
    suspend fun deletePlanetsRemoteKeys()
}