package com.oyj.mediasearch.data.remote.media

import com.oyj.mediasearch.data.local.dto.image.KakaoImageDto
import com.oyj.mediasearch.data.local.dto.video.KakaoVideoDto
import com.oyj.mediasearch.data.remote.network.KakaoApiService
import retrofit2.Response
import javax.inject.Inject

class MediaRemoteDataSourceImpl @Inject constructor(
    private val kakaoApiService: KakaoApiService,
) : MediaRemoteDataSource {
    override suspend fun getImageMedia(query: String, page: Int): Response<KakaoImageDto> {
        return kakaoApiService.searchImage(query, SORT_MODE, page, PAGE_SIZE/2)
    }

    override suspend fun getVideoMedia(query: String, page: Int): Response<KakaoVideoDto> {
        return kakaoApiService.searchVideo(query, SORT_MODE, page, PAGE_SIZE/2)
    }

    companion object {
        private const val PAGE_SIZE = 150
        private const val SORT_MODE = "recency"
    }
}