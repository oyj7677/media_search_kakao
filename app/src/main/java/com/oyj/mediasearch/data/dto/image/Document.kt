package com.oyj.mediasearch.data.dto.image

import kotlinx.serialization.Serializable

@Serializable
data class Document(
    val collection: String,
    val datetime: String,
    val display_sitename: String,
    val doc_url: String,
    val height: Int,
    val image_url: String,
    val thumbnail_url: String,
    val width: Int
)