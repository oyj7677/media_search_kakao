package com.oyj.mediasearch.domain.model

sealed class Media(
    open val thumbnail: String,
    open val dateTime: String,
    open val mediaUrl: String,
    open val sources: String,
    open val isBookmark: Boolean
)

data class MediaImage(
    override val thumbnail: String,
    override val dateTime: String,
    override val mediaUrl: String,
    override val sources: String,
    override val isBookmark: Boolean,
    val imgUrl: String,
) : Media(
    thumbnail = thumbnail,
    dateTime = dateTime,
    mediaUrl = mediaUrl,
    sources = sources,
    isBookmark = false,
)

data class MediaVideo(
    override val thumbnail: String,
    override val dateTime: String,
    override val mediaUrl: String,
    override val sources: String,
    override val isBookmark: Boolean,
    val title: String,
    val playTime: Int,
) : Media(
    thumbnail = thumbnail,
    dateTime = dateTime,
    mediaUrl = mediaUrl,
    sources = sources,
    isBookmark = false,
)