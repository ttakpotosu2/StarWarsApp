package com.example.starwarsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles_remote_keys_table")
data class VehiclesRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prev: Int?,
    val next: Int?
)