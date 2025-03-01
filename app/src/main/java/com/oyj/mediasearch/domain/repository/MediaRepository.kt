package com.oyj.mediasearch.domain.repository

import androidx.paging.PagingData
import com.oyj.mediasearch.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getMedia(query: String): Flow<PagingData<Media>>
    fun getBookmarkList(): Flow<PagingData<Media>>
    suspend fun insertBookmark(media: Media)
    suspend fun deleteBookmark(mediaUrl: String, thumbnail: String)
    suspend fun isBookmarked(mediaUrl: String, thumbnail: String): Boolean
}