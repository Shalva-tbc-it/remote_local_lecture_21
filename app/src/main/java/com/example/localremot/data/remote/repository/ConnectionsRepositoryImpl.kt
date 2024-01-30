package com.example.localremot.data.remote.repository

import com.example.localremot.data.common.HandleResponse
import com.example.localremot.data.common.Resource
import com.example.localremot.data.remote.mapper.base.asResource
import com.example.localremot.data.remote.mapper.connection.toDomain
import com.example.localremot.data.remote.service.ConnectionsService
import com.example.localremot.domain.model.GetConnection
import com.example.localremot.domain.repository.remote.ConnectionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConnectionsRepositoryImpl @Inject constructor(
    private val connectionsService: ConnectionsService,
    private val handleResponse: HandleResponse
): ConnectionsRepository {
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