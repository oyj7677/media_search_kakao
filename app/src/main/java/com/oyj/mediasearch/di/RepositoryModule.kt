package com.oyj.mediasearch.di

import com.oyj.mediasearch.domain.repository.MediaRepository
import com.oyj.mediasearch.data.repository.MediaRepositoryImpl
import com.oyj.mediasearch.data.local.bookmark.BookmarkDataSource
import com.oyj.mediasearch.data.local.bookmark.BookmarkDataSourceImpl
import com.oyj.mediasearch.data.local.media.MediaLocalDataSource
import com.oyj.mediasearch.data.local.media.MediaLocalDataSourceImpl
import com.oyj.mediasearch.data.remote.media.MediaRemoteDataSource
import com.oyj.mediasearch.data.remote.media.MediaRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMediaRepository(repository: MediaRepositoryImpl): MediaRepository

    @Binds
    abstract fun bindMediaRemoteSource(source: MediaRemoteDataSourceImpl): MediaRemoteDataSource

    @Binds
    abstract fun bindMediaLocalSource(source: MediaLocalDataSourceImpl): MediaLocalDataSource

    @Binds
    abstract fun bindBookmarkDataSource(source: BookmarkDataSourceImpl): BookmarkDataSource
}