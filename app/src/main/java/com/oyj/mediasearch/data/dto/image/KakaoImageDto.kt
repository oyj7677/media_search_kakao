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
            thumbnail = it.thumbnailUrl,
            dateTime = it.datetime,
            mediaUrl = it.imageUrl,
            sources = it.displaySiteName,
            imgUrl = it.imageUrl
        )
    }
}