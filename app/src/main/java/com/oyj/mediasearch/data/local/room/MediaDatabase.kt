package com.oyj.mediasearch.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oyj.mediasearch.data.local.room.dao.BookmarkDao
import com.oyj.mediasearch.data.local.room.dao.MediaDao
import com.oyj.mediasearch.data.local.room.entity.BookmarkEntity
import com.oyj.mediasearch.data.local.room.entity.MediaEntity
import com.oyj.mediasearch.data.local.room.dao.MediaRemoteKeyDao
import com.oyj.mediasearch.data.local.room.entity.MediaRemoteKeyEntity
import com.oyj.mediasearch.data.local.room.view.MediaWithBookmarkView

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