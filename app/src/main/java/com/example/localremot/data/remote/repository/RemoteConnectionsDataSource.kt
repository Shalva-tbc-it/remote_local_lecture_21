package com.example.localremot.data.remote.repository

import com.example.localremot.data.remote.common.HandleResponse
import com.example.localremot.data.remote.common.Resource
import com.example.localremot.data.remote.mapper.base.asResource
import com.example.localremot.data.remote.mapper.connection.toDomain
import com.example.localremot.data.remote.service.ConnectionsService
import com.example.localremot.domain.model.GetConnection
import com.example.localremot.domain.repository.remote.RemoteConnectionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteConnectionsDataSource @Inject constructor(
    private val connectionsService: ConnectionsService,
    private val handleResponse: HandleResponse
): RemoteConnectionsRepository {
    override suspend fun getConnections(): Flow<Resource<List<GetConnection>>> {
        return handleResponse.safeApiCall {
            connectionsService.getConnections()
        }.asResource { connectionList ->
            connectionList.map {
                it.toDomain()
            }
        }
    }

}