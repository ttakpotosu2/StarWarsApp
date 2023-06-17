package com.example.starwarsapp.di

import android.content.Context
import androidx.room.Room
import com.example.starwarsapp.data.local.StarWarsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): StarWarsDatabase{
        return Room.databaseBuilder(
            context,
            StarWarsDatabase::class.java,
            "star_wars_project_database"
        ).build()
    }
}