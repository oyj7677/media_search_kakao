package com.oyj.mediasearch.data.repository.remote

import com.oyj.mediasearch.data.model.Media

interface MediaRemoteSource {
    fun searchMedia(keyword: String): List<Media>
}