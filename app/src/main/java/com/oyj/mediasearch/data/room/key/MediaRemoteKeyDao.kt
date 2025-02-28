package com.oyj.mediasearch.data.room.key

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MediaRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<MediaRemoteKeyEntity>)

    @Query("SELECT * FROM MediaRemoteKeyEntity WHERE id = :id")
    suspend fun getMediaRemoteKey(id: Long): MediaRemoteKeyEntity

    @Query("DELETE FROM MediaRemoteKeyEntity")
    suspend fun clearMediaRemoteKeys()
}