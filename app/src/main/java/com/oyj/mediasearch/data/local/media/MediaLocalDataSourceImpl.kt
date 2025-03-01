package com.oyj.mediasearch.data.local.media

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.oyj.mediasearch.data.MediaCategory
import com.oyj.mediasearch.domain.model.Media
import com.oyj.mediasearch.data.local.room.MediaDatabase
import com.oyj.mediasearch.data.local.room.entity.MediaEntity
import com.oyj.mediasearch.data.local.room.view.MediaWithBookmarkView
import com.oyj.mediasearch.data.local.room.entity.MediaRemoteKeyEntity
import com.oyj.mediasearch.util.mapper.toMediaEntity
import javax.inject.Inject

class MediaLocalDataSourceImpl @Inject constructor(
    private val database: MediaDatabase
) : MediaLocalDataSource {

    override suspend fun clearMedia() {
        database.mediaDao().clearMedia()
    }

    override suspend fun insertMediaList(mediaList: List<MediaEntity>) {
        database.mediaDao().insertMediaList(mediaList)
    }

    override fun getMediaView(): PagingSource<Int, MediaWithBookmarkView> {
        return database.mediaDao().getMediaView()
    }

    override suspend fun getBookmarkedMedia(): PagingSource<Int, MediaEntity> {
        return database.mediaDao().getMedia()
    }

    override suspend fun insertRemoteKeyList(remoteKeys: List<MediaRemoteKeyEntity>) {
        database.mediaRemoteKeyDao().insertMediaRemoteKeyList(remoteKeys)
    }

    override suspend fun getMediaRemoteKey(id: Long): MediaRemoteKeyEntity {
        return database.mediaRemoteKeyDao().getMediaRemoteKey(id)
    }

    override suspend fun clearMediaRemoteKeys() {
        database.mediaRemoteKeyDao().clearMediaRemoteKeys()
    }

    override suspend fun saveMediaAndKeys(
        mediaList: List<Media>,
        page: Int,
        loadType: LoadType,
        isEnd: Boolean,
        category: MediaCategory
    ) {
        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                if(category == MediaCategory.Image) {
                    clearMedia()
                }
                clearMediaRemoteKeys()
            }

            val prevKey = if (page == MEDIA_START_PAEGING_INDEX) null else page - 1
            val nextKey = if (mediaList.isEmpty() || isEnd) null else page + 1

            insertRemoteKey(
                mediaList = mediaList,
                prevKey = prevKey,
                nextKey = nextKey
            )

            insertMediaList(
                mediaList.map {
                    it.toMediaEntity()
                }
            )
        }
    }

    private suspend fun insertRemoteKey(
        mediaList: List<Media>,
        prevKey: Int?,
        nextKey: Int?
    ) {
        val keys = mediaList.map {
            MediaRemoteKeyEntity(
                prevKey = prevKey,
                nextKey = nextKey,
            )
        }
        insertRemoteKeyList(keys)
    }


    companion object {
        private const val MEDIA_START_PAEGING_INDEX = 1
    }
}