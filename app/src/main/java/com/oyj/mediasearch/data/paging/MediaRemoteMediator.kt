package com.oyj.mediasearch.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.oyj.mediasearch.data.MediaCategory
import com.oyj.mediasearch.data.local.media.MediaLocalDataSource
import com.oyj.mediasearch.data.remote.media.MediaRemoteDataSource
import com.oyj.mediasearch.data.local.room.view.MediaWithBookmarkView
import com.oyj.mediasearch.data.local.room.entity.MediaImageRemoteKeyEntity
import com.oyj.mediasearch.data.local.room.entity.MediaVideoRemoteKeyEntity
import com.oyj.mediasearch.util.mapper.toMediaImageList
import com.oyj.mediasearch.util.mapper.toMediaVideoList

@OptIn(ExperimentalPagingApi::class)
class MediaRemoteMediator(
    private val query: String,
    private val mediaLocalDataSource: MediaLocalDataSource,
    private val mediaRemoteDataSource: MediaRemoteDataSource,
) : RemoteMediator<Int, MediaWithBookmarkView>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MediaWithBookmarkView>
    ): MediatorResult {
        val mediaImagePage: Int = when (loadType) {
            LoadType.REFRESH -> {
                // 데이터 첫 로드
                val remoteKey = getMediaImageRemoteKeyInCurrentItem(state)
                remoteKey?.nextKey?.minus(1) ?: MEDIA_START_PAEGING_INDEX
            }

            LoadType.PREPEND -> {
                // 데이터 시작 부분
                val remoteKey = getMediaImageRemoteKeyInFirstItem(state)
                val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                prevKey
            }

            LoadType.APPEND -> {
                // 데이터 끝
                val remoteKey = getMediaImageRemoteKeyInLastItem(state)
                val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                nextKey
            }
        }

        try {
            val response = mediaRemoteDataSource.getImageMedia(query, mediaImagePage)
            val body = response.body()
            val imageMediaList = body?.toMediaImageList() ?: emptyList()
            val isEnd = body?.meta?.isEnd ?: true

            val response1 = mediaRemoteDataSource.getVideoMedia(query, mediaImagePage)
            val body1 = response1.body()
            val imageMediaList1 = body1?.toMediaVideoList() ?: emptyList()
            val isEnd1 = body1?.meta?.isEnd ?: true

            val combinedList = (imageMediaList + imageMediaList1).sortedByDescending { it.dateTime }

            mediaLocalDataSource.saveMediaAndKeys(
                mediaList = combinedList,
                page = mediaImagePage,
                loadType = loadType,
                isEnd = isEnd && isEnd1,
                category = MediaCategory.Image,
            )
            return MediatorResult.Success(imageMediaList.isEmpty())

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getMediaImageRemoteKeyInFirstItem(state: PagingState<Int, MediaWithBookmarkView>): MediaImageRemoteKeyEntity? {
        return state.firstItemOrNull()?.let { mediaEntity ->
            mediaLocalDataSource.getMediaImageRemoteKey(
                id = mediaEntity.id
            )
        }
    }

    private suspend fun getMediaImageRemoteKeyInCurrentItem(state: PagingState<Int, MediaWithBookmarkView>): MediaImageRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                mediaLocalDataSource.getMediaImageRemoteKey(
                    id = id
                )
            }
        }
    }

    private suspend fun getMediaImageRemoteKeyInLastItem(state: PagingState<Int, MediaWithBookmarkView>): MediaImageRemoteKeyEntity? {
        return state.lastItemOrNull()?.let { mediaEntity ->
            mediaLocalDataSource.getMediaImageRemoteKey(
                id = mediaEntity.id
            )
        }
    }

    private suspend fun getMediaVideoRemoteKeyInFirstItem(state: PagingState<Int, MediaWithBookmarkView>): MediaVideoRemoteKeyEntity? {
        return state.firstItemOrNull()?.let { mediaEntity ->
            mediaLocalDataSource.getMediaVideoRemoteKey(
                id = mediaEntity.id
            )
        }
    }

    private suspend fun getMediaVideoRemoteKeyInCurrentItem(state: PagingState<Int, MediaWithBookmarkView>): MediaVideoRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                mediaLocalDataSource.getMediaVideoRemoteKey(
                    id = id
                )
            }
        }
    }

    private suspend fun getMediaVideoRemoteKeyInLastItem(state: PagingState<Int, MediaWithBookmarkView>): MediaVideoRemoteKeyEntity? {
        return state.lastItemOrNull()?.let { mediaEntity ->
            mediaLocalDataSource.getMediaVideoRemoteKey(
                id = mediaEntity.id
            )
        }
    }

    companion object {
        private const val MEDIA_START_PAEGING_INDEX = 1
        private const val TAG = "MediaRemoteMediator"
    }
}