package com.example.localremot.presentation.event

sealed class ConnectionEvent {
    object FetchConnections : ConnectionEvent()
    object LogOut : ConnectionEvent()
    object ResetErrorMessage : ConnectionEvent()
}
