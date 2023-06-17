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
import com.example.starwarsapp.domain.models.FilmsEntity
import com.example.starwarsapp.domain.models.FilmsRemoteKeys
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.domain.models.PeopleRemoteKeys
import com.example.starwarsapp.domain.models.PlanetsEntity
import com.example.starwarsapp.domain.models.PlanetsRemoteKeys
import com.example.starwarsapp.domain.models.SpeciesEntity
import com.example.starwarsapp.domain.models.SpeciesRemoteKeys
import com.example.starwarsapp.domain.models.StarshipsEntity
import com.example.starwarsapp.domain.models.StarshipsRemoteKeys
import com.example.starwarsapp.domain.models.VehiclesEntity
import com.example.starwarsapp.domain.models.VehiclesRemoteKeys

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

    //People
    abstract fun filmsDao(): FilmsDao
    abstract fun filmsRemoteKeysDao(): FilmsRemoteKeysDao

    //People
    abstract fun planetsDao(): PlanetsDao
    abstract fun planetsRemoteKeysDao(): PlanetsRemoteKeysDao

    //People
    abstract fun speciesDao(): SpeciesDao
    abstract fun speciesRemoteKeysDao(): SpeciesRemoteKeysDao

    //People
    abstract fun starshipsDao(): StarshipsDao
    abstract fun starshipsRemoteKeysDao(): StarshipsRemoteKeysDao

    //People
    abstract fun vehiclesDao(): VehiclesDao
    abstract fun vehiclesRemoteKeysDao(): VehiclesRemoteKeysDao
}