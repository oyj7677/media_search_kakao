package com.oyj.mediasearch.data.local.media

import androidx.paging.LoadType
import androidx.paging.PagingSource
import com.oyj.mediasearch.data.MediaCategory
import com.oyj.mediasearch.domain.model.Media
import com.oyj.mediasearch.data.local.room.entity.MediaEntity
import com.oyj.mediasearch.data.local.room.view.MediaWithBookmarkView
import com.oyj.mediasearch.data.local.room.entity.MediaImageRemoteKeyEntity
import com.oyj.mediasearch.data.local.room.entity.MediaVideoRemoteKeyEntity

interface MediaLocalDataSource {
    suspend fun clearMedia()
    suspend fun insertMediaList(mediaList: List<MediaEntity>)
    fun getMediaView(): PagingSource<Int, MediaWithBookmarkView>
    suspend fun getBookmarkedMedia(): PagingSource<Int, MediaEntity>
    suspend fun insertImageRemoteKeyList(remoteKeys: List<MediaImageRemoteKeyEntity>)
    suspend fun insertVideoRemoteKeyList(remoteKeys: List<MediaVideoRemoteKeyEntity>)
    suspend fun getMediaImageRemoteKey(id: Long): MediaImageRemoteKeyEntity
    suspend fun getMediaVideoRemoteKey(id: Long): MediaVideoRemoteKeyEntity
    suspend fun clearMediaRemoteKeys(category: MediaCategory)

    suspend fun saveMediaAndKeys(
        mediaList: List<Media>,
        page: Int,
        loadType: LoadType,
        isEnd: Boolean,
        category : MediaCategory
    )
}