package com.oyj.mediasearch.di

import com.oyj.mediasearch.data.repository.MediaRepository
import com.oyj.mediasearch.data.repository.MediaRepositoryImpl
import com.oyj.mediasearch.data.repository.local.BookmarkDataSource
import com.oyj.mediasearch.data.repository.local.BookmarkDataSourceImpl
import com.oyj.mediasearch.data.repository.local.MediaLocalDataSource
import com.oyj.mediasearch.data.repository.local.MediaLocalDataSourceImpl
import com.oyj.mediasearch.data.repository.remote.MediaRemoteDataSource
import com.oyj.mediasearch.data.repository.remote.MediaRemoteDataSourceImpl
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