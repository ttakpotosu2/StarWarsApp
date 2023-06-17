package com.example.starwarsapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "species_remote_keys_table")
data class SpeciesRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prev: Int?,
    val next: Int?
)