package com.example.localremot.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.localremot.data.local.dao.ConnectionDao
import com.example.localremot.data.local.entity.ConnectionEntity

@Database(
    entities = [ConnectionEntity::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun connectionDao(): ConnectionDao

}