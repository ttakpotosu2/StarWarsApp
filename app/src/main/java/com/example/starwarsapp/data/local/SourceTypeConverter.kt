package com.example.starwarsapp.data.local

import androidx.room.TypeConverter
import com.example.starwarsapp.data.models.PeopleEntity
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

    @TypeConverter
    fun fromPeopleEntity(source: PeopleEntity): String{
        return Json.encodeToString(source)
    }

    @TypeConverter
    fun toPeopleEntity(source: String): PeopleEntity{
        return  Json.decodeFromString(source)
    }
}