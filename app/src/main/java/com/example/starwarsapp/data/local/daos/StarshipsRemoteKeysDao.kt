package com.example.starwarsapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.domain.models.StarshipsRemoteKeys

@Dao
interface StarshipsRemoteKeysDao {

    @Query("SELECT * FROM starships_remote_keys_table WHERE id = :id")
    fun getStarshipsRemoteKeys(id: String): StarshipsRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStarshipsRemoteKeys(remoteKeys: List<StarshipsRemoteKeys>)

    @Query("DELETE FROM starships_remote_keys_table")
    suspend fun deleteStarshipsRemoteKeys()
}