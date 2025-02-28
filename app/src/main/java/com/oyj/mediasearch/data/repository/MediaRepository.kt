package com.oyj.mediasearch.data.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.room.BookmarkEntity
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getMedia(query: String): Flow<PagingData<Media>>
    suspend fun insertBookmark(media: Media)
    suspend fun deleteBookmark(mediaUrl: String, thumbnail: String)
    suspend fun getBookmarkList(): PagingSource<Int, BookmarkEntity>
    suspend fun isBookmarked(mediaUrl: String, thumbnail: String): Boolean
}