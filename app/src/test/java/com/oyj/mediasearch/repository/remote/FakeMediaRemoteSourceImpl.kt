package com.oyj.mediasearch.repository.remote

import com.oyj.mediasearch.data.dto.image.KakaoImageDto
import com.oyj.mediasearch.data.dto.image.toMediaImageList
import com.oyj.mediasearch.data.dto.video.KakaoVideoDto
import com.oyj.mediasearch.data.dto.video.toMediaVideoList
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSource
import com.oyj.mediasearch.network.KakaoApiService
import kotlinx.serialization.json.Json
import java.io.File

class FakeMediaRemoteSourceImpl(): MediaRemoteSource {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend  fun searchImage(keyword: String): List<Media> {
        val response = File("src/test/java/com/oyj/mediasearch/response/searchImage_노머스_Response.json").readText()
        val resultKakaoImageDto = json.decodeFromString<KakaoImageDto>(response)
        return resultKakaoImageDto.toMediaImageList()
    }

    override suspend  fun searchVideo(keyword: String): List<Media> {
        val response = File("src/test/java/com/oyj/mediasearch/response/searchVideo_노머스_Response.json").readText()
        val resultKakaoImageDto = json.decodeFromString<KakaoVideoDto>(response)
        for (document in resultKakaoImageDto.documents) {
            println(document)
        }
        return resultKakaoImageDto.toMediaVideoList()
    }
}