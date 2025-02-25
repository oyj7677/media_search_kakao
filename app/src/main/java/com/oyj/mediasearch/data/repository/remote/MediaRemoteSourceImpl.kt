package com.oyj.mediasearch.data.repository.remote

import com.oyj.mediasearch.data.model.Media

class MediaRemoteSourceImpl : MediaRemoteSource {
    override fun searchImage(keyword: String): List<Media> {
        return emptyList()
    }
}