package com.example.localremot.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.localremot.data.local.entity.ConnectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConnectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(connectionEntity: List<ConnectionEntity>)

    @Query("SELECT * FROM items")
    fun getAll() : Flow<List<ConnectionEntity>>

    @Query("SELECT * FROM items WHERE category is :category ")
    fun getCategory(category: String): Flow<List<ConnectionEntity>>

}