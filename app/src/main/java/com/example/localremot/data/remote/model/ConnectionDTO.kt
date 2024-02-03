package com.example.localremot.data.remote.model

import com.squareup.moshi.Json

data class ConnectionDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "cover")
    val cover: String,
    @Json(name = "price")
    val price: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "favorite")
    val favorite: Boolean,
    @Json(name = "category")
    var category: String
)
