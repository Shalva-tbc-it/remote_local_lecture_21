package com.example.localremot.domain.repository.remote

import com.example.localremot.data.remote.common.Resource
import com.example.localremot.domain.model.GetConnection
import kotlinx.coroutines.flow.Flow

interface RemoteConnectionsRepository {
    suspend fun getConnections(): Flow<Resource<List<GetConnection>>>

}