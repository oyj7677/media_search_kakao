package com.oyj.mediasearch.data.repository

import androidx.paging.PagingData
import com.oyj.mediasearch.data.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun searchImagePaging(query: String): Flow<PagingData<Media>>
}