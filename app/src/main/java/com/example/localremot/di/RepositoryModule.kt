package com.example.localremot.di

import com.example.localremot.data.common.HandleResponse
import com.example.localremot.data.local.repository.ConnectionRepositoryImpl
import com.example.localremot.data.remote.repository.ConnectionsRepositoryImpl
import com.example.localremot.data.remote.service.ConnectionsService
import com.example.localremot.domain.repository.local.ConnectionRepository
import com.example.localremot.domain.repository.remote.ConnectionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideConnectionsRepository(
        connectionsService: ConnectionsService,
        handleResponse: HandleResponse
    ): ConnectionsRepository {
        return ConnectionsRepositoryImpl(
            connectionsService = connectionsService,
            handleResponse = handleResponse
        )
    }

    @Singleton
    @Provides
    fun provideConnectionRepositoryDb(connectionRepositoryImpl: ConnectionRepositoryImpl) : ConnectionRepository {
        return connectionRepositoryImpl
    }
}