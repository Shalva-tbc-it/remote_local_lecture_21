package com.example.localremot.data.local.repository

import com.example.localremot.data.local.dao.ConnectionDao
import com.example.localremot.data.local.mapper.toData
import com.example.localremot.data.local.mapper.toDomain
import com.example.localremot.domain.model.GetConnection
import com.example.localremot.domain.repository.local.DbConnectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DbConnectionDataSource @Inject constructor(
    private var connectionDao: ConnectionDao
): DbConnectionRepository {

    override suspend fun insertItem(getConnection: List<GetConnection>) {
            connectionDao.insertItem(getConnection.map {
                it.toData()
            })
    }

    override suspend fun getAll(): Flow<List<GetConnection>> {
        return connectionDao.getAll().map { connectionList ->
            connectionList.map {
                it.toDomain()
            }
        }
    }


}