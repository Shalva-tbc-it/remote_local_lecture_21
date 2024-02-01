package com.example.localremot.di

import com.example.localremot.data.common.NetworkStatus
import com.example.localremot.data.local.repository.DbConnectionDataSource
import com.example.localremot.data.remote.common.HandleResponse
import com.example.localremot.data.remote.repository.RemoteConnectionsDataSource
import com.example.localremot.data.remote.service.ConnectionsService
import com.example.localremot.data.repository.GetConnectionsRepositoryImpl
import com.example.localremot.domain.repository.GetConnectionsRepository
import com.example.localremot.domain.repository.local.DbConnectionRepository
import com.example.localremot.domain.repository.remote.RemoteConnectionsRepository
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
    ): RemoteConnectionsRepository {
        return RemoteConnectionsDataSource(
            connectionsService = connectionsService,
            handleResponse = handleResponse
        )
    }

    @Singleton
    @Provides
    fun provideConnectionRepositoryDb(connectionDataSource: DbConnectionDataSource) : DbConnectionRepository {
        return connectionDataSource
    }

    @Singleton
    @Provides
    fun provideConnections(
        connectionsRepository: RemoteConnectionsRepository,
        connectionRepository: DbConnectionRepository,
        networkStatus: NetworkStatus
    ): GetConnectionsRepository {
        return GetConnectionsRepositoryImpl(
            connectionRepository = connectionRepository,
            connectionsRepository = connectionsRepository,
            networkStatus = networkStatus
        )
    }
}