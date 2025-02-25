package com.oyj.mediasearch.data.dto.image

import com.oyj.mediasearch.data.model.MediaImage
import kotlinx.serialization.Serializable

@Serializable
data class KakaoImageDto(
    val documents: List<Document>,
    val meta: Meta
)

fun KakaoImageDto.toMediaImageList(): List<MediaImage> {
    return documents.map {
        MediaImage(
            thumbnail = it.thumbnail_url,
            dateTime = it.datetime,
            mediaUrl = it.image_url,
            sources = it.display_sitename,
            imgUrl = it.image_url
        )
    }
}