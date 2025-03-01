package com.oyj.mediasearch.data.repository.local

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.model.toBookmarkEntity
import com.oyj.mediasearch.data.model.toMediaEntity
import com.oyj.mediasearch.data.room.MediaDatabase
import com.oyj.mediasearch.data.room.MediaEntity
import com.oyj.mediasearch.data.room.MediaWithBookmarkView
import com.oyj.mediasearch.data.room.key.MediaRemoteKeyEntity
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
    // 북마크와 join?

    override suspend fun getBookmarkedMedia(): PagingSource<Int, MediaEntity> {
        return database.mediaDao().getMedia()
    }

    override suspend fun insertAllKey(remoteKeys: List<MediaRemoteKeyEntity>) {
        database.mediaRemoteKeyDao().insertAll(remoteKeys)
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
    ) {
        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                clearMedia()
                clearMediaRemoteKeys()
            }

            val prevKey = if (page == MEDIA_START_PAEGING_INDEX) null else page - 1
            val nextKey = if (mediaList.isEmpty() || isEnd) null else page + 1
            val keys = mediaList.map {
                MediaRemoteKeyEntity(
                    prevKey = prevKey,
                    nextKey = nextKey,
                )
            }
            insertAllKey(keys)
            insertMediaList(
                mediaList.map {
                    it.toMediaEntity()
                }
            )
        }
    }

    companion object {
        private const val MEDIA_START_PAEGING_INDEX = 1
    }
}