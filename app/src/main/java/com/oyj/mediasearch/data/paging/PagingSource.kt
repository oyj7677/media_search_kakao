package com.oyj.mediasearch.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oyj.mediasearch.data.dto.image.toMediaImageList
import com.oyj.mediasearch.data.dto.video.toMediaVideoList
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.network.KakaoApiService
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class PagingSource(
    private val kakaoApiService: KakaoApiService,
    private val query: String,
    private val sort: String,
) : PagingSource<Int, Media>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val imageResponse = kakaoApiService.searchImage(
                query = query,
                sort = sort,
                page = page,
                size = PAGE_SIZE
            )
            val imageBody = imageResponse.body()
            val image = imageBody?.toMediaImageList() ?: emptyList()
            val isEndImage = imageBody?.meta?.isEnd ?: true

            val videoResponse = kakaoApiService.searchVideo(
                query = query,
                sort = sort,
                page = page,
                size = PAGE_SIZE
            )
            val videoBody = videoResponse.body()
            val video = videoBody?.toMediaVideoList() ?: emptyList()
            val isEndVideo = videoBody?.meta?.isEnd ?: true

            val media = image + video

            LoadResult.Page(
                data = sortDateListDescending(media),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (isEndImage && isEndVideo) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    private fun sortDateListDescending(dates: List<Media>): List<Media> {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        return dates.sortedByDescending { media ->
            OffsetDateTime.parse(media.dateTime, formatter)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val PAGE_SIZE = 100
    }
}