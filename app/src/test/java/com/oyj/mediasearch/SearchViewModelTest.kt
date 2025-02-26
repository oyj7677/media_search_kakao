package com.oyj.mediasearch

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.oyj.mediasearch.data.repository.MediaRepository
import com.oyj.mediasearch.data.repository.MediaRepositoryImpl
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSource
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSourceImpl
import com.oyj.mediasearch.network.KakaoApiService
import com.oyj.mediasearch.ui.search.SearchViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.create

class SearchViewModelTest {
    private val server = MockWebServer()
    private lateinit var service: KakaoApiService

    private lateinit var mediaRemoteSource: MediaRemoteSource
    private lateinit var mediaRepository: MediaRepository
    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun init() {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        service = Retrofit
            .Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create()

        mediaRemoteSource = MediaRemoteSourceImpl(
            kakaoApiService = service
        )
        mediaRepository = MediaRepositoryImpl(
            mediaRemoteSource = mediaRemoteSource
        )
        searchViewModel = SearchViewModel(
            repository = mediaRepository
        )
    }

    @Test
    fun `사용자_검색어_입력_테스트`() {
        // Given : 사용자가 검색어를 입력한다.
        val keyword = "노머스"

        // When : 사용자가 검색어를 입력하면 쿼리를 세팅을 한다
        searchViewModel.setQuery(keyword)

        // Then : 검색어가 세팅되어야 한다.
        assertThat(searchViewModel.query.value).isEqualTo(keyword)
    }
}