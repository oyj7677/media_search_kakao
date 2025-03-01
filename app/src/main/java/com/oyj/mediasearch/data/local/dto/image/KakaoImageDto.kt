package com.oyj.mediasearch.data.local.dto.image

import kotlinx.serialization.Serializable

@Serializable
data class KakaoImageDto(
    val documents: List<Document>,
    val meta: Meta
)
