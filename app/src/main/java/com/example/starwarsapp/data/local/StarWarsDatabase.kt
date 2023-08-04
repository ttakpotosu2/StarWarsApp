package com.example.starwarsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.starwarsapp.data.local.daos.FilmsDao
import com.example.starwarsapp.data.local.daos.FilmsRemoteKeysDao
import com.example.starwarsapp.data.local.daos.PeopleDao
import com.example.starwarsapp.data.local.daos.PeopleRemoteKeysDao
import com.example.starwarsapp.data.local.daos.PlanetsDao
import com.example.starwarsapp.data.local.daos.PlanetsRemoteKeysDao
import com.example.starwarsapp.data.local.daos.SpeciesDao
import com.example.starwarsapp.data.local.daos.SpeciesRemoteKeysDao
import com.example.starwarsapp.data.local.daos.StarshipsDao
import com.example.starwarsapp.data.local.daos.StarshipsRemoteKeysDao
import com.example.starwarsapp.data.local.daos.VehiclesDao
import com.example.starwarsapp.data.local.daos.VehiclesRemoteKeysDao
import com.example.starwarsapp.data.models.FilmsEntity
import com.example.starwarsapp.data.models.FilmsRemoteKeys
import com.example.starwarsapp.data.models.PeopleEntity
import com.example.starwarsapp.data.models.PeopleRemoteKeys
import com.example.starwarsapp.data.models.PlanetsEntity
import com.example.starwarsapp.data.models.PlanetsRemoteKeys
import com.example.starwarsapp.data.models.SpeciesEntity
import com.example.starwarsapp.data.models.SpeciesRemoteKeys
import com.example.starwarsapp.data.models.StarshipsEntity
import com.example.starwarsapp.data.models.StarshipsRemoteKeys
import com.example.starwarsapp.data.models.VehiclesEntity
import com.example.starwarsapp.data.models.VehiclesRemoteKeys

@Database(
    entities = [
        FilmsEntity::class,
        FilmsRemoteKeys::class,
        PeopleEntity::class,
        PeopleRemoteKeys::class,
        PlanetsEntity::class,
        PlanetsRemoteKeys::class,
        SpeciesEntity::class,
        SpeciesRemoteKeys::class,
        StarshipsEntity::class,
        StarshipsRemoteKeys::class,
        VehiclesEntity::class,
        VehiclesRemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(SourceTypeConverter::class)
abstract class StarWarsDatabase : RoomDatabase() {

    //People
    abstract fun peopleDao(): PeopleDao
    abstract fun peopleRemoteKeysDao(): PeopleRemoteKeysDao

    //Films
    abstract fun filmsDao(): FilmsDao
    abstract fun filmsRemoteKeysDao(): FilmsRemoteKeysDao

    //Planets
    abstract fun planetsDao(): PlanetsDao
    abstract fun planetsRemoteKeysDao(): PlanetsRemoteKeysDao

    //Species
    abstract fun speciesDao(): SpeciesDao
    abstract fun speciesRemoteKeysDao(): SpeciesRemoteKeysDao

    //Starships
    abstract fun starshipsDao(): StarshipsDao
    abstract fun starshipsRemoteKeysDao(): StarshipsRemoteKeysDao

    //Vehicles
    abstract fun vehiclesDao(): VehiclesDao
    abstract fun vehiclesRemoteKeysDao(): VehiclesRemoteKeysDao
}