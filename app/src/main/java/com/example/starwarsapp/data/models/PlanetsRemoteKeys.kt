package com.example.starwarsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planets_remote_keys_table")
data class PlanetsRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prev: Int?,
    val next: Int?
)