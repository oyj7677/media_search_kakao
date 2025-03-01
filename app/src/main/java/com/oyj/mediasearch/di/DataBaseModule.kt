package com.oyj.mediasearch.di

import android.content.Context
import androidx.room.Room
import com.oyj.mediasearch.data.room.BookmarkDao
import com.oyj.mediasearch.data.room.MediaDao
import com.oyj.mediasearch.data.room.MediaDatabase
import com.oyj.mediasearch.data.room.key.MediaRemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Singleton
    @Provides
    fun provideMediaDatabase(
        @ApplicationContext context: Context,
    ): MediaDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MediaDatabase::class.java,
            "Media_Database.db",
        ).build()
    }

    @Singleton
    @Provides
    fun provideMediaDao(mediaDatabase: MediaDatabase): MediaDao {
        return mediaDatabase.mediaDao()
    }

    @Singleton
    @Provides
    fun provideMediaRemoteKeyDao(mediaDatabase: MediaDatabase): MediaRemoteKeyDao {
        return mediaDatabase.mediaRemoteKeyDao()
    }

    @Singleton
    @Provides
    fun provideBookmarkDao(mediaDatabase: MediaDatabase): BookmarkDao {
        return mediaDatabase.bookmarkDao()
    }
}
