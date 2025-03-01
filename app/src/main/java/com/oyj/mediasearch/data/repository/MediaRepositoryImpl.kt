package com.oyj.mediasearch.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.model.toBookmarkEntity
import com.oyj.mediasearch.data.paging.MediaRemoteMediator
import com.oyj.mediasearch.data.repository.local.BookmarkDataSource
import com.oyj.mediasearch.data.repository.local.MediaLocalDataSource
import com.oyj.mediasearch.data.repository.remote.MediaRemoteDataSource
import com.oyj.mediasearch.data.room.toMedia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaRemoteDataSource: MediaRemoteDataSource,
    private val mediaLocalDataSource: MediaLocalDataSource,
    private val bookmarkDatasource: BookmarkDataSource
) : MediaRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getMedia(query: String): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            remoteMediator = MediaRemoteMediator(
                query = query,
                mediaLocalDataSource = mediaLocalDataSource,
                mediaRemoteDataSource = mediaRemoteDataSource
            )
        ) {
            mediaLocalDataSource.getMediaView()
        }.flow.map { pagingData ->
            pagingData.map { mediaEntity ->
                mediaEntity.toMedia()
            }
        }
    }

    override suspend fun insertBookmark(media: Media) {
        bookmarkDatasource.insertBookmark(media.toBookmarkEntity())
    }

    override suspend fun deleteBookmark(mediaUrl: String, thumbnail: String) {
        bookmarkDatasource.deleteBookmark(mediaUrl, thumbnail)
    }

    override fun getBookmarkList(): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                bookmarkDatasource.getBookmarkList()
            }
        ).flow.map { pagingData ->
            pagingData.map { bookmarkEntity ->
                bookmarkEntity.toMedia() // BookmarkEntity를 Media로 변환
            }
        }
    }

    override suspend fun isBookmarked(mediaUrl: String, thumbnail: String): Boolean {
        return bookmarkDatasource.isBookmarked(mediaUrl, thumbnail)
    }

    companion object {
        private const val PAGE_SIZE = 150
    }
}