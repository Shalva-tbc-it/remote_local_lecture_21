package com.example.localremot.domain.model

import com.squareup.moshi.Json

data class GetConnection(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val favorite: Boolean
)
