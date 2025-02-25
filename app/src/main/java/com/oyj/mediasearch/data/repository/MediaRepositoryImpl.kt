package com.oyj.mediasearch.data.repository

import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSource

class MediaRepositoryImpl(
    private val mediaRemoteSource: MediaRemoteSource,
) : MediaRepository {

    override fun searchMedia(keyword: String): List<Media> {
        return mediaRemoteSource.searchMedia(keyword)
    }
}