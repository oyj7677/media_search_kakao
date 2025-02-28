package com.oyj.mediasearch.data.repository.local

import androidx.paging.PagingSource
import com.oyj.mediasearch.data.room.BookmarkEntity
import com.oyj.mediasearch.data.room.MediaDatabase
import javax.inject.Inject

class BookmarkDataSourceImpl @Inject constructor(
    private val database: MediaDatabase
) : BookmarkDataSource {
    override suspend fun insertBookmark(bookmarkEntity: BookmarkEntity) {
        database.bookmarkDao().insertBookmark(bookmarkEntity)
    }

    override suspend fun deleteBookmark(mediaUrl: String, thumbnail: String) {
        database.bookmarkDao().deleteBookmark(
            mediaUrl = mediaUrl,
            thumbnail = thumbnail
        )
    }

    override suspend fun getBookmarkList(): PagingSource<Int, BookmarkEntity> {
        return database.bookmarkDao().getBookmarkList()
    }

    override suspend fun isBookmarked(mediaUrl: String, thumbnail: String): Boolean {
        return database.bookmarkDao().isBookmarked(
            mediaUrl = mediaUrl,
            thumbnail = thumbnail
        )
    }
}