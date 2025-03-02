package com.oyj.mediasearch.data.remote.media

import com.oyj.mediasearch.data.local.dto.image.KakaoImageDto
import com.oyj.mediasearch.data.local.dto.video.KakaoVideoDto
import retrofit2.Response

interface MediaRemoteDataSource {
    suspend fun getImageMedia(query: String, page: Int): Response<KakaoImageDto>
    suspend fun getVideoMedia(query: String, page: Int): Response<KakaoVideoDto>
}