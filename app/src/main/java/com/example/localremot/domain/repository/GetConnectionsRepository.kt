package com.example.localremot.domain.repository

import com.example.localremot.data.remote.common.Resource
import com.example.localremot.domain.model.GetConnection
import kotlinx.coroutines.flow.Flow

interface GetConnectionsRepository {
    suspend fun getItems(): Flow<Resource<List<GetConnection>>>
}