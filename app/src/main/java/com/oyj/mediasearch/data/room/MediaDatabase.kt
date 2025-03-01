package com.oyj.mediasearch.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oyj.mediasearch.data.room.key.MediaRemoteKeyDao
import com.oyj.mediasearch.data.room.key.MediaRemoteKeyEntity

@Database(
    version = 1,
    entities = [
        MediaEntity::class,
        MediaRemoteKeyEntity::class,
        BookmarkEntity::class,
    ],
    views = [
        MediaWithBookmarkView::class,
    ]
)
abstract class MediaDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun mediaRemoteKeyDao(): MediaRemoteKeyDao
    abstract fun bookmarkDao(): BookmarkDao
}