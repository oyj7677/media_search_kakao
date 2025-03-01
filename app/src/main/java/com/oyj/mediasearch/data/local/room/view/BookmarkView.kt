package com.oyj.mediasearch.data.local.room.view

import androidx.room.DatabaseView

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