package com.oyj.mediasearch.data.repository.remote

import com.oyj.mediasearch.data.dto.image.toMediaImageList
import com.oyj.mediasearch.data.dto.video.toMediaVideoList
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.network.KakaoApiService
import javax.inject.Inject

class MediaRemoteSourceImpl @Inject constructor(
    private val kakaoApiService: KakaoApiService
) : MediaRemoteSource {
    override suspend fun searchImage(keyword: String): List<Media> {
        val response = kakaoApiService.searchImage(
            query = keyword,
            sort = "recency",
            page = 1,
            size = 30
        )

        return if (response.isSuccessful) {
            response.body()!!.toMediaImageList()
        } else {
            emptyList()
        }
    }

    override suspend fun searchVideo(keyword: String): List<Media> {
        val response = kakaoApiService.searchVideo(
            query = keyword,
            sort = "recency",
            page = 1,
            size = 30
        )

        return if (response.isSuccessful) {
            response.body()!!.toMediaVideoList()
        } else {
            emptyList()
        }
    }

    companion object {
        private const val TAG = "MediaRemoteSourceImpl"
    }
}