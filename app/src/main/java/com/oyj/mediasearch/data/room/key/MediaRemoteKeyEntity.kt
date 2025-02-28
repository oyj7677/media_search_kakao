package com.oyj.mediasearch.data.room.key

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaRemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val prevKey: Int?,
    val nextKey: Int?,
)
