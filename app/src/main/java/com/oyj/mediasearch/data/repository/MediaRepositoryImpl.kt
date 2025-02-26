package com.oyj.mediasearch.data.repository

import androidx.paging.PagingData
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaRemoteSource: MediaRemoteSource
) : MediaRepository {

    override fun searchImagePaging(query: String): Flow<PagingData<Media>> {
        return mediaRemoteSource.pagingImage(query)
    }
}