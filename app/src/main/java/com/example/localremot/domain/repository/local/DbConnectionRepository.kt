package com.example.localremot.domain.repository.local

import com.example.localremot.domain.model.GetConnection
import kotlinx.coroutines.flow.Flow

interface DbConnectionRepository {
    suspend fun insertItem(getConnection: List<GetConnection>)

    suspend fun getAll() : Flow<List<GetConnection>>

    suspend fun getCategory(category: String) : Flow<List<GetConnection>>
}