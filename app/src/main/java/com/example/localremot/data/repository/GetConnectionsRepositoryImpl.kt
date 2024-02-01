package com.example.localremot.data.repository

import com.example.localremot.data.common.NetworkStatus
import com.example.localremot.data.remote.common.Resource
import com.example.localremot.domain.model.GetConnection
import com.example.localremot.domain.repository.GetConnectionsRepository
import com.example.localremot.domain.repository.local.DbConnectionRepository
import com.example.localremot.domain.repository.remote.RemoteConnectionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetConnectionsRepositoryImpl @Inject constructor(
    private val connectionRepository: DbConnectionRepository,
    private val connectionsRepository: RemoteConnectionsRepository,
    private val networkStatus: NetworkStatus
): GetConnectionsRepository {
    override suspend fun getItems(): Flow<Resource<List<GetConnection>>> = flow {
        networkStatus.networkStatus.collect { status ->
            if (status) {
                connectionsRepository.getConnections().collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            emit(resource)
                        }
                        is Resource.Success -> {
                            connectionRepository.insertItem(resource.data)
                            emit(resource)
                        }
                        is Resource.Error -> {
                            emit(resource)
                        }
                    }
                }
            } else {
                connectionRepository.getAll().collect {
                    emit(Resource.Success(it))
                }
            }
        }
    }
}