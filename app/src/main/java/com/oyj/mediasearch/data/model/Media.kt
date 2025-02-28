package com.oyj.mediasearch.data.model

import com.oyj.mediasearch.data.room.BookmarkEntity
import com.oyj.mediasearch.data.room.MediaEntity


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

fun Media.toMediaEntity(): MediaEntity {
    return when (this) {
        is MediaImage -> MediaEntity(
            id = 0,
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            imgUrl = imgUrl
        )

        is MediaVideo -> MediaEntity(
            id = 0,
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            title = title,
            playTime = playTime
        )
    }
}

fun Media.toBookmarkEntity(): BookmarkEntity {
    return when (this) {
        is MediaImage -> BookmarkEntity(
            id = 0,
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
        )

        is MediaVideo -> BookmarkEntity(
            id = 0,
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            title = title,
            playTime = playTime
        )
    }
}