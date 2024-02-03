package com.example.localremot.domain.model

data class GetConnection(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val favorite: Boolean,
    var category: String
)
