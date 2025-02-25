package com.oyj.mediasearch.data.repository.remote

import com.oyj.mediasearch.data.model.Media

class MediaRemoteSourceImpl : MediaRemoteSource {
    override fun searchMedia(keyword: String): List<Media> {
        return emptyList()
    }
}