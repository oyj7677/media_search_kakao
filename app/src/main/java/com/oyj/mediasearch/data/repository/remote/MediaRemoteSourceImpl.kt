package com.oyj.mediasearch.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.paging.PagingSource
import com.oyj.mediasearch.network.KakaoApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRemoteSourceImpl @Inject constructor(
    private val kakaoApiService: KakaoApiService
) : MediaRemoteSource {

    override fun pagingImage(query: String): Flow<PagingData<Media>> {
        val pagingSourceFactory = {
            PagingSource(
                kakaoApiService = kakaoApiService,
                query = query,
                sort = "recency"
            )
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = PAGE_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 100
    }
}