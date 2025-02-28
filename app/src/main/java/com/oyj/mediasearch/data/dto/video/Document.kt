package com.oyj.mediasearch.data.dto.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Document(
    @SerialName("author")
    val author: String,
    @SerialName("datetime")
    val datetime: String,
    @SerialName("play_time")
    val playTime: Int,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String
)