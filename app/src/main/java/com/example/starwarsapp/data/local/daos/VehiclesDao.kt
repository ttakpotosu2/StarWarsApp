package com.example.starwarsapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.SpeciesEntity
import com.example.starwarsapp.domain.models.VehiclesEntity

@Dao
interface VehiclesDao {

    @Query("SELECT * FROM vehicles_table")
    fun getVehicle(): PagingSource<Int, VehiclesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVehicle(vehicles: List<VehiclesEntity>)

    @Query("DELETE FROM vehicles_table")
    suspend fun deleteVehicle()

    @Query("SELECT * FROM vehicles_table WHERE name = :name ")
    suspend fun getVehicleById(name: String): VehiclesEntity

    @Query("SELECT * FROM vehicles_table WHERE name IN (:name)")
    suspend fun getExtraVehicles(name: List<String>): List<VehiclesEntity>
}