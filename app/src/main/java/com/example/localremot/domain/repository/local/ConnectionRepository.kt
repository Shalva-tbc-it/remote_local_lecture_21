package com.example.localremot.domain.repository.local

import com.example.localremot.domain.model.GetConnection
import kotlinx.coroutines.flow.Flow

interface ConnectionRepository {
    suspend fun insertItem(getConnection: List<GetConnection>)

    suspend fun getAll() : Flow<List<GetConnection>>
}