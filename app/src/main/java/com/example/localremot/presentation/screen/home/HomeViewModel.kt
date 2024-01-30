package com.example.localremot.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localremot.data.common.Resource
import com.example.localremot.domain.usecase.GetConnectionsUseCase
import com.example.localremot.presentation.event.ConnectionEvent
import com.example.localremot.presentation.mapper.toPresentation
import com.example.localremot.presentation.state.ConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getConnectionsUseCase: GetConnectionsUseCase
): ViewModel() {

    private val _connectionState = MutableStateFlow(ConnectionState())
    val connectionState: SharedFlow<ConnectionState> = _connectionState.asStateFlow()

    fun onEvent(event: ConnectionEvent) {
        when (event) {
            is ConnectionEvent.FetchConnections -> fetchConnections()
            is ConnectionEvent.LogOut -> {}
            is ConnectionEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }
    private fun fetchConnections() {
        viewModelScope.launch {
            getConnectionsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _connectionState.update { currentState ->
                        currentState.copy(
                            isLoading = resource.loading
                        )
                    }

                    is Resource.Success -> {
                        _connectionState.update { currentState -> currentState.copy(connections = resource.data.map { it.toPresentation() }) }
                    }

                    is Resource.Error -> updateErrorMessage(message = resource.errorMessage)
                }
            }
        }
    }


    private fun updateErrorMessage(message: String?) {
        _connectionState.update { currentState -> currentState.copy(errorMessage = message) }
    }

}