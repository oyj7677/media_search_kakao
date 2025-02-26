package com.oyj.mediasearch.data.repository.remote

import com.oyj.mediasearch.data.model.Media

interface MediaRemoteSource {
    fun searchImage(keyword: String): List<Media>
    fun searchVideo(keyword: String): List<Media>
}