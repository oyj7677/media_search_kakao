package com.oyj.mediasearch.data.remote.network

import com.oyj.mediasearch.data.local.dto.image.KakaoImageDto
import com.oyj.mediasearch.data.local.dto.video.KakaoVideoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoApiService {

    @GET("image")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<KakaoImageDto>

    @GET("vclip")
    suspend fun searchVideo(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<KakaoVideoDto>
}