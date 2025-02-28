package com.oyj.mediasearch.data.repository.remote

import com.oyj.mediasearch.data.dto.image.KakaoImageDto
import com.oyj.mediasearch.data.dto.video.KakaoVideoDto
import retrofit2.Response

interface MediaRemoteDataSource {
    suspend fun getImageMedia(query: String, page: Int): Response<KakaoImageDto>
    suspend fun getVideoMedia(query: String, page: Int): Response<KakaoVideoDto>
}