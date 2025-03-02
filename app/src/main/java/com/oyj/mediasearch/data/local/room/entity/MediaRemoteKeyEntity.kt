package com.oyj.mediasearch.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaRemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val prevKey: Int?,
    val nextKey: Int?,
)
