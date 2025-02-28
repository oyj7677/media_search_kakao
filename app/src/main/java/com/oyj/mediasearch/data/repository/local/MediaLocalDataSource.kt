package com.oyj.mediasearch.data.repository.local

import androidx.paging.LoadType
import androidx.paging.PagingSource
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.model.MediaImage
import com.oyj.mediasearch.data.room.MediaEntity
import com.oyj.mediasearch.data.room.key.MediaRemoteKeyEntity

interface MediaLocalDataSource {

    suspend fun clearMedia()
    suspend fun insertMediaList(mediaList: List<MediaEntity>)
    fun getMedia(): PagingSource<Int, MediaEntity>
    suspend fun getBookmarkedMedia(): PagingSource<Int, MediaEntity>

    suspend fun insertAllKey(remoteKeys: List<MediaRemoteKeyEntity>)
    suspend fun getMediaRemoteKey(id: Long): MediaRemoteKeyEntity?
    suspend fun clearMediaRemoteKeys()

    suspend fun saveMediaAndKeys(mediaList: List<Media>, page: Int, loadType: LoadType, isEnd: Boolean)
}