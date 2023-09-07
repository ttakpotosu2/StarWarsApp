package com.example.starwarsapp.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SourceTypeConverter {

    @TypeConverter
    fun fromString(source: String): List<String>{
        return Json.decodeFromString(source)
    }

    @TypeConverter
    fun toString(list: List<String>): String{
        return Json.encodeToString(list)
    }
}