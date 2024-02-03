package com.example.localremot.presentation.model

data class Connection(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val favorite: Boolean,
    var category: String
)