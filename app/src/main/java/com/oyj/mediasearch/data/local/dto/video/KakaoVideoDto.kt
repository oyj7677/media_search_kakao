package com.oyj.mediasearch.data.local.dto.video

import kotlinx.serialization.Serializable

@Serializable
data class KakaoVideoDto(
    val documents: List<Document>,
    val meta: Meta
)