package com.oyj.mediasearch.data.local.media

import android.util.Log
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.oyj.mediasearch.data.MediaCategory
import com.oyj.mediasearch.domain.model.Media
import com.oyj.mediasearch.data.local.room.MediaDatabase
import com.oyj.mediasearch.data.local.room.entity.MediaEntity
import com.oyj.mediasearch.data.local.room.view.MediaWithBookmarkView
import com.oyj.mediasearch.data.local.room.entity.MediaImageRemoteKeyEntity
import com.oyj.mediasearch.data.local.room.entity.MediaVideoRemoteKeyEntity
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

    override suspend fun insertImageRemoteKeyList(remoteKeys: List<MediaImageRemoteKeyEntity>) {
        database.mediaRemoteKeyDao().insertMediaImageRemoteKeyList(remoteKeys)
    }

    override suspend fun insertVideoRemoteKeyList(remoteKeys: List<MediaVideoRemoteKeyEntity>) {
        database.mediaRemoteKeyDao().insertMediaVideoRemoteKeyList(remoteKeys)
    }

    override suspend fun getMediaImageRemoteKey(id: Long): MediaImageRemoteKeyEntity {
        return database.mediaRemoteKeyDao().getMediaImageRemoteKey(id)
    }

    override suspend fun getMediaVideoRemoteKey(id: Long): MediaVideoRemoteKeyEntity {
        return database.mediaRemoteKeyDao().getMediaVideoRemoteKey(id)
    }

    override suspend fun clearMediaRemoteKeys(
        category: MediaCategory
    ) {
        when (category) {
            MediaCategory.Image -> database.mediaRemoteKeyDao().clearMediaImageRemoteKeys()
            MediaCategory.Video -> database.mediaRemoteKeyDao().clearMediaVideoRemoteKeys()
        }
    }

    override suspend fun saveMediaAndKeys(
        mediaList: List<Media>,
        page: Int,
        loadType: LoadType,
        isEnd: Boolean,
        category: MediaCategory
    ) {
        database.withTransaction {
            Log.d(TAG, "saveMediaAndKeys: $category")
            Log.d(TAG, "saveMediaAndKeys: $loadType")
            if (loadType == LoadType.REFRESH) {
                if(category == MediaCategory.Image) {
                    clearMedia()
                }
                clearMediaRemoteKeys(category)
            }

            val prevKey = if (page == MEDIA_START_PAEGING_INDEX) null else page - 1
            val nextKey = if (mediaList.isEmpty() || isEnd) null else page + 1

            insertRemoteKey(category, mediaList, prevKey, nextKey)

            insertMediaList(
                mediaList.map {
                    it.toMediaEntity()
                }
            )
        }
    }

    private suspend fun insertRemoteKey(
        category: MediaCategory,
        mediaList: List<Media>,
        prevKey: Int?,
        nextKey: Int?
    ) {
        when (category) {
            MediaCategory.Image -> {
                val keys = mediaList.map {
                    MediaImageRemoteKeyEntity(
                        prevKey = prevKey,
                        nextKey = nextKey,
                    )
                }
                insertImageRemoteKeyList(keys)
            }

            MediaCategory.Video -> {
                val keys = mediaList.map {
                    MediaVideoRemoteKeyEntity(
                        prevKey = prevKey,
                        nextKey = nextKey,
                    )
                }
                insertVideoRemoteKeyList(keys)
            }
        }
    }

    companion object {
        private const val MEDIA_START_PAEGING_INDEX = 1
        private const val TAG = "MediaLocalDataSourceImp"
    }
}