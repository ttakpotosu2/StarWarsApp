package com.example.starwarsapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.models.VehiclesRemoteKeys

@Dao
interface VehiclesRemoteKeysDao {

    @Query("SELECT * FROM vehicles_remote_keys_table WHERE id = :id")
    fun getVehiclesRemoteKeys(id: String): VehiclesRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVehiclesRemoteKeys(remoteKeys: List<VehiclesRemoteKeys>)

    @Query("DELETE FROM vehicles_remote_keys_table")
    suspend fun deleteVehiclesRemoteKeys()
}