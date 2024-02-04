package com.example.localremot.presentation.event

sealed class ConnectionEvent {
    data object FetchConnections : ConnectionEvent()
    data class GetCategory(var category: String) : ConnectionEvent()
    data object SetCategoryMenu : ConnectionEvent()
    data object ResetErrorMessage : ConnectionEvent()

}
