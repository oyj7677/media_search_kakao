package com.oyj.mediasearch.data.repository.remote

import androidx.paging.PagingData
import com.oyj.mediasearch.data.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRemoteSource {
    fun pagingImage(query: String): Flow<PagingData<Media>>
}