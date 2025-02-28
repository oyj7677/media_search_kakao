package com.oyj.mediasearch.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.paging.MediaRemoteMediator
import com.oyj.mediasearch.data.paging.MediaVideoRemoteMediator
import com.oyj.mediasearch.data.repository.local.MediaLocalDataSource
import com.oyj.mediasearch.data.repository.remote.MediaRemoteDataSource
import com.oyj.mediasearch.data.room.toMedia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaRemoteDataSource: MediaRemoteDataSource,
    private val mediaLocalDataSource: MediaLocalDataSource
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
            mediaLocalDataSource.getMedia()
        }.flow.map { pagingData ->
            pagingData.map { mediaEntity ->
                mediaEntity.toMedia()
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 150
    }
}