package com.example.localremot.domain.repository

import com.example.localremot.data.common.Resource
import com.example.localremot.domain.model.GetConnection
import kotlinx.coroutines.flow.Flow

interface ConnectionsRepository {
    suspend fun getConnections(): Flow<Resource<List<GetConnection>>>

}