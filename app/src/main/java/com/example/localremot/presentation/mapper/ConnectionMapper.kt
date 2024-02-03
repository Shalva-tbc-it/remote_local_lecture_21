package com.example.localremot.presentation.mapper

import com.example.localremot.domain.model.GetConnection
import com.example.localremot.presentation.model.Connection

fun GetConnection.toPresentation() =
    Connection(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite,
        category = category
    )

fun Connection.toDomain() =
    GetConnection(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite,
        category = category
    )