package com.oyj.mediasearch.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oyj.mediasearch.data.model.Media
import com.oyj.mediasearch.data.model.MediaImage
import com.oyj.mediasearch.data.model.MediaVideo

@Entity
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val thumbnail: String,
    val dateTime: String,
    val mediaUrl: String,
    val sources: String,
    val imgUrl: String? = null,
    val title: String? = null,
    val playTime: Int? = null,
)

fun BookmarkEntity.toMedia(): Media {
    return if (imgUrl != null) {
        MediaImage(
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            imgUrl = imgUrl
        )
    } else {
        MediaVideo(
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            title = title!!,
            playTime = playTime!!
        )
    }
}