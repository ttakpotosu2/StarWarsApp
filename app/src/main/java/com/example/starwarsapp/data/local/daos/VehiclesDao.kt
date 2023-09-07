package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.starwarsapp.data.models.VehiclesEntity

@Dao
interface VehiclesDao {

    @Query("SELECT * FROM vehicles_table")
    fun getVehicle(): PagingSource<Int, VehiclesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVehicles(vehicles: List<VehiclesEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateVehicles(vehicles: List<VehiclesEntity>)

    @Query("DELETE FROM vehicles_table")
    suspend fun deleteVehicle()

    @Query("SELECT * FROM vehicles_table WHERE name = :name ")
    suspend fun getVehicleById(name: String): VehiclesEntity

    @Query("SELECT * FROM vehicles_table WHERE url IN (:urls)")
    suspend fun getExtraVehicles(urls: List<String>): List<VehiclesEntity>
}