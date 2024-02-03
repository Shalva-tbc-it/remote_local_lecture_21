package com.example.localremot.data.remote.mapper.connection

import com.example.localremot.data.remote.model.ConnectionDTO
import com.example.localremot.domain.model.GetConnection

fun ConnectionDTO.toDomain() =
    GetConnection(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite,
        category = category
    )
