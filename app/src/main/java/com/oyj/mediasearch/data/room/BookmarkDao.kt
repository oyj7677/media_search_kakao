package com.oyj.mediasearch.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity)

    @Query("DELETE FROM BookmarkEntity WHERE mediaUrl = :mediaUrl And thumbnail= :thumbnail")
    suspend fun deleteBookmark(mediaUrl: String, thumbnail: String)

    @Query("SELECT * FROM BookmarkEntity")
    fun getBookmarkList(): PagingSource<Int, BookmarkEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM BookmarkEntity WHERE mediaUrl = :mediaUrl And thumbnail= :thumbnail)")
    suspend fun isBookmarked(mediaUrl: String, thumbnail: String): Boolean
}
