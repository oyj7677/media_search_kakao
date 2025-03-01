package com.oyj.mediasearch.data.local.bookmark

import androidx.paging.PagingSource
import com.oyj.mediasearch.data.local.room.entity.BookmarkEntity

interface BookmarkDataSource {
    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity)
    suspend fun deleteBookmark(mediaUrl: String, thumbnail: String)
    fun getBookmarkList(): PagingSource<Int, BookmarkEntity>
    suspend fun isBookmarked(mediaUrl: String, thumbnail: String): Boolean
}