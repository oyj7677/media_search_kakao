package com.oyj.mediasearch.data.dto.image

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val total_count: Int
)