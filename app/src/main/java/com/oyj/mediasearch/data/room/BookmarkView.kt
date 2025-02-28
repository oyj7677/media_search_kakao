package com.oyj.mediasearch.data.room

import androidx.room.DatabaseView
import com.oyj.mediasearch.data.model.MediaImage
import com.oyj.mediasearch.data.model.MediaVideo
import com.oyj.mediasearch.data.model.Media

@DatabaseView(
    viewName = "media_with_bookmark_view",
    value = """
        SELECT 
            MediaEntity.id AS id,
            MediaEntity.thumbnail AS thumbnail,
            MediaEntity.dateTime AS dateTime,
            MediaEntity.mediaUrl AS mediaUrl,
            MediaEntity.sources AS sources,
            MediaEntity.imgUrl AS imgUrl,
            MediaEntity.title AS title,
            MediaEntity.playTime AS playTime,
            CASE 
                WHEN BookmarkEntity.mediaUrl IS NOT NULL THEN 1 
                ELSE 0 
            END AS isBookmark
        FROM MediaEntity
        LEFT JOIN BookmarkEntity ON MediaEntity.mediaUrl = BookmarkEntity.mediaUrl
    """
)
data class MediaWithBookmarkView(
    val id: Long,
    val thumbnail: String,
    val dateTime: String,
    val mediaUrl: String,
    val sources: String,
    val imgUrl: String? = null,
    val title: String? = null,
    val playTime: Int,
    val isBookmark: Boolean
)

fun MediaWithBookmarkView.toMedia(): Media {
    return if (title != null) {
        MediaVideo(
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            isBookmark = isBookmark,
            title = title,
            playTime = playTime
        )
    } else {
        MediaImage(
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            imgUrl = imgUrl!!,
            isBookmark = isBookmark
        )
    }
}