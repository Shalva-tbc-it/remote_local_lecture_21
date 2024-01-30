package com.example.localremot.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items")
data class ConnectionEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "cover")
    var cover: String,
    @ColumnInfo(name = "price")
    var price: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean
)
