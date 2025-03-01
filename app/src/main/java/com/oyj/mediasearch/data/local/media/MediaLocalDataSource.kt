package com.oyj.mediasearch.data.local.media

import androidx.paging.LoadType
import androidx.paging.PagingSource
import com.oyj.mediasearch.domain.model.Media
import com.oyj.mediasearch.data.local.room.entity.MediaEntity
import com.oyj.mediasearch.data.local.room.view.MediaWithBookmarkView
import com.oyj.mediasearch.data.local.room.entity.MediaRemoteKeyEntity

interface MediaLocalDataSource {
    suspend fun clearMedia()
    suspend fun insertMediaList(mediaList: List<MediaEntity>)
    fun getMediaView(): PagingSource<Int, MediaWithBookmarkView>
    suspend fun getBookmarkedMedia(): PagingSource<Int, MediaEntity>
    suspend fun insertAllKey(remoteKeys: List<MediaRemoteKeyEntity>)
    suspend fun getMediaRemoteKey(id: Long): MediaRemoteKeyEntity?
    suspend fun clearMediaRemoteKeys()

    suspend fun saveMediaAndKeys(
        mediaList: List<Media>,
        page: Int,
        loadType: LoadType,
        isEnd: Boolean,
    )
}