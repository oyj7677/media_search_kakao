package com.oyj.mediasearch.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oyj.mediasearch.data.local.room.entity.MediaRemoteKeyEntity

@Dao
interface MediaRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaRemoteKeyList(remoteKeys: List<MediaRemoteKeyEntity>)

    @Query("SELECT * FROM MediaRemoteKeyEntity WHERE id = :id")
    suspend fun getMediaRemoteKey(id: Long): MediaRemoteKeyEntity

    @Query("DELETE FROM MediaRemoteKeyEntity")
    suspend fun clearMediaRemoteKeys()
}