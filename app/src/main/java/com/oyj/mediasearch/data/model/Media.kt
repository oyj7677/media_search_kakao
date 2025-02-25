package com.oyj.mediasearch.data.model

sealed class Media(
    open val thumbnail: String,
    open val dateTime: Int,
    open val mediaUrl: String,
    open val sources: String,
)

data class Image(
    override val thumbnail: String,
    override val dateTime: Int,
    override val mediaUrl: String,
    override val sources: String,
    val imgUrl: String,
) : Media(
    thumbnail = thumbnail,
    dateTime = dateTime,
    mediaUrl = mediaUrl,
    sources = sources,
)

data class Video(
    override val thumbnail: String,
    override val dateTime: Int,
    override val mediaUrl: String,
    override val sources: String,
    val title: String,
    val playTime: Int,
) : Media(
    thumbnail = thumbnail,
    dateTime = dateTime,
    mediaUrl = mediaUrl,
    sources = sources,
)
