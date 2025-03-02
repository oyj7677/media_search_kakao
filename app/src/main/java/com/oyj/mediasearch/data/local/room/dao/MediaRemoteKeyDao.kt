package com.oyj.mediasearch.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oyj.mediasearch.data.local.room.entity.MediaImageRemoteKeyEntity
import com.oyj.mediasearch.data.local.room.entity.MediaVideoRemoteKeyEntity

@Dao
interface MediaRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaImageRemoteKeyList(remoteKeys: List<MediaImageRemoteKeyEntity>)

    @Query("SELECT * FROM MediaImageRemoteKeyEntity WHERE id = :id")
    suspend fun getMediaImageRemoteKey(id: Long): MediaImageRemoteKeyEntity

    @Query("DELETE FROM MediaImageRemoteKeyEntity")
    suspend fun clearMediaImageRemoteKeys()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaVideoRemoteKeyList(remoteKeys: List<MediaVideoRemoteKeyEntity>)

    @Query("SELECT * FROM MediaVideoRemoteKeyEntity WHERE id = :id")
    suspend fun getMediaVideoRemoteKey(id: Long): MediaVideoRemoteKeyEntity

    @Query("DELETE FROM MediaVideoRemoteKeyEntity")
    suspend fun clearMediaVideoRemoteKeys()
}