package com.oyj.mediasearch.data.repository

import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaRemoteSource: MediaRemoteSource
) : MediaRepository {
    override suspend fun searchMedia(keyword: String): List<Media> {
        return coroutineScope {
            val imageList = async {
                mediaRemoteSource.searchImage(keyword)
            }
            val videoList = async {
                mediaRemoteSource.searchVideo(keyword)
            }
            try {
                imageList.await() + videoList.await()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}