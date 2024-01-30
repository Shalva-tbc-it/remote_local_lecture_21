package com.example.localremot.di

import android.content.Context
import androidx.room.Room
import com.example.localremot.data.local.dao.ConnectionDao
import com.example.localremot.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "items_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideConnectionDao(appDatabase: AppDatabase): ConnectionDao {
        return appDatabase.connectionDao()
    }

}