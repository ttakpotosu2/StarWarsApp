package com.example.starwarsapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.domain.models.FilmsRemoteKeys

@Dao
interface FilmsRemoteKeysDao {

    @Query("SELECT * FROM films_remote_keys_table WHERE id = :id")
    fun getFilmsRemoteKeys(id: String): FilmsRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilmsRemoteKeys(remoteKeys: List<FilmsRemoteKeys>)

    @Query("DELETE FROM films_remote_keys_table")
    suspend fun deleteFilmsRemoteKeys()
}