package com.oyj.mediasearch.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.oyj.mediasearch.data.MediaCategory
import com.oyj.mediasearch.data.local.media.MediaLocalDataSource
import com.oyj.mediasearch.data.remote.media.MediaRemoteDataSource
import com.oyj.mediasearch.data.local.room.view.MediaWithBookmarkView
import com.oyj.mediasearch.data.local.room.entity.MediaRemoteKeyEntity
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
        val mediaPage: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getMediaRemoteKeyInCurrentItem(state)
                remoteKey?.nextKey?.minus(1) ?: MEDIA_START_PAGING_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKey = getMediaRemoteKeyInFirstItem(state)
                val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKey = getMediaRemoteKeyInLastItem(state)
                val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                nextKey
            }
        }

        try {
            val imageResponse = mediaRemoteDataSource.getImageMedia(query, mediaPage)
            val imageBody = imageResponse.body()
            val imageMediaList = imageBody?.toMediaImageList() ?: emptyList()
            val imageDataIsEnd = imageBody?.meta?.isEnd ?: true

            val videoResponse = mediaRemoteDataSource.getVideoMedia(query, mediaPage)
            val videoBody = videoResponse.body()
            val videoMediaList = videoBody?.toMediaVideoList() ?: emptyList()
            val videoDataIsEnd = videoBody?.meta?.isEnd ?: true

            val combinedList = (imageMediaList + videoMediaList).sortedByDescending { it.dateTime }

            mediaLocalDataSource.saveMediaAndKeys(
                mediaList = combinedList,
                page = mediaPage,
                loadType = loadType,
                isEnd = imageDataIsEnd && videoDataIsEnd,
                category = MediaCategory.Image,
            )
            return MediatorResult.Success(
                endOfPaginationReached = imageMediaList.isEmpty() && videoMediaList.isEmpty()
            )

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getMediaRemoteKeyInFirstItem(state: PagingState<Int, MediaWithBookmarkView>): MediaRemoteKeyEntity? {
        return state.firstItemOrNull()?.let { mediaEntity ->
            mediaLocalDataSource.getMediaRemoteKey(
                id = mediaEntity.id
            )
        }
    }

    private suspend fun getMediaRemoteKeyInCurrentItem(state: PagingState<Int, MediaWithBookmarkView>): MediaRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                mediaLocalDataSource.getMediaRemoteKey(
                    id = id
                )
            }
        }
    }

    private suspend fun getMediaRemoteKeyInLastItem(state: PagingState<Int, MediaWithBookmarkView>): MediaRemoteKeyEntity? {
        return state.lastItemOrNull()?.let { mediaEntity ->
            mediaLocalDataSource.getMediaRemoteKey(
                id = mediaEntity.id
            )
        }
    }

    companion object {
        private const val MEDIA_START_PAGING_INDEX = 1
    }
}