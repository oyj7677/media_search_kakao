package com.oyj.mediasearch.data.dto.video

import com.oyj.mediasearch.data.model.MediaVideo
import kotlinx.serialization.Serializable

@Serializable
data class KakaoVideoDto(
    val documents: List<Document>,
    val meta: Meta
)

fun KakaoVideoDto.toMediaVideoList(): List<MediaVideo> {
    return documents.map {
        MediaVideo(
            thumbnail = it.thumbnail,
            dateTime = it.datetime,
            mediaUrl = it.url,
            sources = it.author,
            title = it.title,
            playTime = it.playTime,
            isBookmark = false
        )
    }
}

