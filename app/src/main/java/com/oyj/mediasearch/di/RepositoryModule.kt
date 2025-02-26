package com.oyj.mediasearch.di

import com.oyj.mediasearch.data.repository.MediaRepository
import com.oyj.mediasearch.data.repository.MediaRepositoryImpl
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSource
import com.oyj.mediasearch.data.repository.remote.MediaRemoteSourceImpl
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
    abstract fun bindMediaRemoteSource(source: MediaRemoteSourceImpl): MediaRemoteSource
}