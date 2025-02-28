package com.oyj.mediasearch.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.oyj.mediasearch.data.dto.image.toMediaImageList
import com.oyj.mediasearch.data.repository.local.MediaLocalDataSource
import com.oyj.mediasearch.data.repository.remote.MediaRemoteDataSource
import com.oyj.mediasearch.data.room.MediaWithBookmarkView
import com.oyj.mediasearch.data.room.key.MediaRemoteKeyEntity

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
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                // 데이터 첫 로드
                val remoteKey = getRemoteKeyInCurrentItem(state)
                remoteKey?.nextKey?.minus(1) ?: MEDIA_START_PAEGING_INDEX
            }

            LoadType.PREPEND -> {
                // 데이터 시작 부분
                val remoteKey = getRemoteKeyInFirstItem(state)
                val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                prevKey
            }

            LoadType.APPEND -> {
                // 데이터 끝
                val remoteKey = getRemoteKeyInLastItem(state)
                val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                nextKey
            }
        }

        try {
            val response = mediaRemoteDataSource.getImageMedia(query, page)
            val body = response.body()
            val mediaList = body?.toMediaImageList() ?: emptyList()
            val isEnd = body?.meta?.isEnd ?: true

            mediaLocalDataSource.saveMediaAndKeys(
                mediaList = mediaList,
                page = page,
                loadType = loadType,
                isEnd = isEnd,
            )

            return MediatorResult.Success(mediaList.isEmpty())

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyInFirstItem(state: PagingState<Int, MediaWithBookmarkView>): MediaRemoteKeyEntity? {
        return state.firstItemOrNull()?.let { mediaEntity ->
            mediaLocalDataSource.getMediaRemoteKey(
                id = mediaEntity.id
            )
        }
    }

    private suspend fun getRemoteKeyInCurrentItem(state: PagingState<Int, MediaWithBookmarkView>): MediaRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                mediaLocalDataSource.getMediaRemoteKey(
                    id = id
                )
            }
        }
    }

    private suspend fun getRemoteKeyInLastItem(state: PagingState<Int, MediaWithBookmarkView>): MediaRemoteKeyEntity? {
        return state.lastItemOrNull()?.let { mediaEntity ->
            mediaLocalDataSource.getMediaRemoteKey(
                id = mediaEntity.id
            )
        }
    }

    companion object {
        private const val MEDIA_START_PAEGING_INDEX = 1
    }
}