package com.example.starwarsapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films_remote_keys_table")
data class FilmsRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prev: Int?,
    val next: Int?
)