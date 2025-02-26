package com.oyj.mediasearch.data.repository

import com.oyj.mediasearch.data.model.Media

interface MediaRepository {
    suspend fun searchImage(keyword: String): List<Media>
    suspend fun searchVideo(keyword: String): List<Media>
}