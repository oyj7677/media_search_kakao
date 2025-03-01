package com.oyj.mediasearch.data.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.oyj.mediasearch.data.local.room.view.MediaWithBookmarkView
import com.oyj.mediasearch.data.local.room.entity.MediaEntity

@Dao
interface MediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(mediaEntity: MediaEntity)

    @Transaction
    suspend fun insertMediaList(mediaList: List<MediaEntity>) {
        mediaList.forEach {
            insertMedia(it)
        }
    }

    @Query("DELETE FROM MediaEntity")
    suspend fun clearMedia()

    @Query("SELECT * FROM MediaEntity")
    fun getMedia(): PagingSource<Int, MediaEntity>

    @Query("SELECT * FROM media_with_bookmark_view")
    fun getMediaView(): PagingSource<Int, MediaWithBookmarkView>
}