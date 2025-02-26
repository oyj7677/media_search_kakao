package com.oyj.mediasearch.data.repository

import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSource
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaRemoteSource: MediaRemoteSource
) : MediaRepository {

    override suspend fun searchImage(keyword: String): List<Media> {
        return mediaRemoteSource.searchImage(keyword)
    }

    override suspend fun searchVideo(keyword: String): List<Media> {
        return mediaRemoteSource.searchVideo(keyword)
    }
}