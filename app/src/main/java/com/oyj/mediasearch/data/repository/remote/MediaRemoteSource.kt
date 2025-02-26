package com.oyj.mediasearch.data.repository.remote

import com.oyj.mediasearch.data.model.Media

interface MediaRemoteSource {
    suspend fun searchImage(keyword: String): List<Media>
    suspend fun searchVideo(keyword: String): List<Media>
}