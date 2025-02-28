package com.oyj.mediasearch.data.model

import com.oyj.mediasearch.data.room.MediaEntity

sealed class Media(
    open val thumbnail: String,
    open val dateTime: String,
    open val mediaUrl: String,
    open val sources: String,
)

data class MediaImage(
    override val thumbnail: String,
    override val dateTime: String,
    override val mediaUrl: String,
    override val sources: String,
    val imgUrl: String,
) : Media(
    thumbnail = thumbnail,
    dateTime = dateTime,
    mediaUrl = mediaUrl,
    sources = sources,
)

data class MediaVideo(
    override val thumbnail: String,
    override val dateTime: String,
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

fun Media.toMediaEntity(): MediaEntity {
    return MediaEntity(
        id = 0,
        thumbnail = thumbnail,
        dateTime = dateTime,
        mediaUrl = mediaUrl,
        sources = sources,
        imgUrl = (this as? MediaImage)?.imgUrl,
        title = (this as? MediaVideo)?.title,
        playTime = (this as? MediaVideo)?.playTime,
    )
}