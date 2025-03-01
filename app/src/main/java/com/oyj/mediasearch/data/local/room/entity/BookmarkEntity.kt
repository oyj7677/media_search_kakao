package com.oyj.mediasearch.data.local.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["thumbnail", "imgUrl"], unique = true)]
)
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

