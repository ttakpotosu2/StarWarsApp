package com.example.starwarsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people_remote_keys_table")
data class PeopleRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prev: Int?,
    val next: Int?
)