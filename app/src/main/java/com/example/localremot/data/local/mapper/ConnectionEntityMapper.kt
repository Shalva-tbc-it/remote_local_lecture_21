package com.example.localremot.data.local.mapper

import com.example.localremot.data.local.entity.ConnectionEntity
import com.example.localremot.domain.model.GetConnection

fun ConnectionEntity.toDomain() =
    GetConnection(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite
    )

fun GetConnection.toData() =
    ConnectionEntity(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite
    )

