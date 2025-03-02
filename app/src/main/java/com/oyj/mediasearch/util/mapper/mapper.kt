package com.oyj.mediasearch.util.mapper

import com.oyj.mediasearch.data.local.dto.image.KakaoImageDto
import com.oyj.mediasearch.data.local.dto.video.KakaoVideoDto
import com.oyj.mediasearch.data.local.room.entity.BookmarkEntity
import com.oyj.mediasearch.data.local.room.entity.MediaEntity
import com.oyj.mediasearch.data.local.room.view.MediaWithBookmarkView
import com.oyj.mediasearch.domain.model.Media
import com.oyj.mediasearch.domain.model.MediaImage
import com.oyj.mediasearch.domain.model.MediaVideo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// 미디어 -> 미디어 엔티티
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

// 미디어 -> 북마크 엔티티
fun Media.toBookmarkEntity(): BookmarkEntity {
    return when (this) {
        is MediaImage -> BookmarkEntity(
            id = 0,
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            imgUrl = imgUrl
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

// 카카오 이미지 dto -> 미디어
fun KakaoImageDto.toMediaImageList(): List<MediaImage> {
    return documents.map {
        MediaImage(
            thumbnail = it.thumbnailUrl,
            dateTime = formatDate(it.datetime),
            mediaUrl = it.imageUrl,
            sources = it.displaySiteName,
            imgUrl = it.imageUrl,
            isBookmark = false
        )
    }
}

// 카카오 비디오 dto -> 미디어 비디오
fun KakaoVideoDto.toMediaVideoList(): List<MediaVideo> {
    return documents.map {
        MediaVideo(
            thumbnail = it.thumbnail,
            dateTime = formatDate(it.datetime),
            mediaUrl = it.url,
            sources = it.author,
            title = it.title,
            playTime = it.playTime,
            isBookmark = false
        )
    }
}

// 북마크 엔티티 -> 미디어
fun BookmarkEntity.toMedia(): Media {
    return if (imgUrl == null) {
        MediaVideo(
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            title = title!!,
            playTime = playTime!!,
            isBookmark = false
        )
    } else {
        MediaImage(
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            imgUrl = imgUrl,
            isBookmark = false
        )
    }
}

// 미디어 북마크 뷰 -> 미디어
fun MediaWithBookmarkView.toMedia(): Media {
    return if (title == null) {
        MediaImage(
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            imgUrl = imgUrl ?: "",
            isBookmark = isBookmark
        )
    } else {
        MediaVideo(
            thumbnail = thumbnail,
            dateTime = dateTime,
            mediaUrl = mediaUrl,
            sources = sources,
            isBookmark = isBookmark,
            title = title,
            playTime = playTime
        )
    }
}

fun formatDate(inputDate: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val date = LocalDateTime.parse(inputDate, formatter)

    val year = date.year
    val month = date.monthValue
    val day = date.dayOfMonth

    return "${year}/${month}/${day}"
}