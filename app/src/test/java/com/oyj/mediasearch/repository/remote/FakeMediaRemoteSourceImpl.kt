package com.oyj.mediasearch.repository.remote

import com.oyj.mediasearch.data.dto.image.KakaoImageDto
import com.oyj.mediasearch.data.dto.image.toMediaImageList
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSource
import kotlinx.serialization.json.Json
import java.io.File

class FakeMediaRemoteSourceImpl: MediaRemoteSource {
    override fun searchImage(keyword: String): List<Media> {
        val response = File("src/test/java/com/oyj/mediasearch/response/searchImage_노머스_Response.json").readText()
        val resultKakaoImageDto = Json.decodeFromString<KakaoImageDto>(response)
        return resultKakaoImageDto.toMediaImageList()
    }
}