package com.oyj.mediasearch.data.repository.local

import androidx.paging.PagingSource
import com.oyj.mediasearch.data.room.BookmarkEntity

interface BookmarkDataSource {
    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity)
    suspend fun deleteBookmark(mediaUrl: String, thumbnail: String)
    suspend fun getBookmarkList(): PagingSource<Int, BookmarkEntity>
    suspend fun isBookmarked(mediaUrl: String, thumbnail: String): Boolean
}