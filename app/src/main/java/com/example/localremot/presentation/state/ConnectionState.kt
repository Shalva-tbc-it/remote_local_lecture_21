package com.example.localremot.presentation.state

import com.example.localremot.presentation.model.Connection

data class ConnectionState(
    val isLoading: Boolean = false,
    val connections: List<Connection>? = null,
    val category: List<String>? = null,
    val errorMessage: String? = null
)
