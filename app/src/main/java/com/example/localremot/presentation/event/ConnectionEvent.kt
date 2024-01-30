package com.example.localremot.presentation.event

sealed class ConnectionEvent {
    data object FetchConnections : ConnectionEvent()
    data object FetchConnectionsFromDb : ConnectionEvent()
    data object ResetErrorMessage : ConnectionEvent()

}
