package com.example.starwarsapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "starships_remote_keys_table")
data class StarshipsRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prev: Int?,
    val next: Int?
)