package com.example.localremot.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localremot.data.remote.common.Resource
import com.example.localremot.domain.usecase.GetConnectionsUseCase
import com.example.localremot.domain.usecase.local.GetCategoryUseCase
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
    private val connectionsUseCase: GetConnectionsUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    private val _connectionState = MutableStateFlow(ConnectionState())
    val connectionState: SharedFlow<ConnectionState> = _connectionState.asStateFlow()

    fun onEvent(event: ConnectionEvent) {
        when (event) {
            is ConnectionEvent.FetchConnections -> fetchConnections()
            is ConnectionEvent.GetCategory -> getCategory(event.category)
            is ConnectionEvent.ResetErrorMessage -> updateErrorMessage(message = null)
            is ConnectionEvent.SetCategoryMenu -> fetchConnectionByCategory()
        }
    }

    private fun getCategory(category: String) {
        viewModelScope.launch {
            getCategoryUseCase.invoke(category).collect {
                _connectionState.update { state ->
                    state.copy(connections = it.map {
                        it.toPresentation()
                    })
                }
            }
        }
    }

    private fun fetchConnections() {
        viewModelScope.launch {
            connectionsUseCase.invoke().collect {
                when (it) {
                    is Resource.Loading -> {
                        _connectionState.update { state ->
                            state.copy(isLoading = it.loading)
                        }
                    }
                    is Resource.Success -> {
                        if (it.data.isEmpty()) {
                            updateErrorMessage(message = "Not Found Items")
                        } else {
                            _connectionState.update { state ->
                                state.copy(
                                    connections = it.data.map { date ->
                                        date.toPresentation() },
                                    isLoading = false
                                )
                            }
                        }


                    }
                    is Resource.Error -> {
                        updateErrorMessage(message = it.errorMessage)
                    }
                }
            }
        }
    }


    private fun fetchConnectionByCategory() {
        viewModelScope.launch {
            connectionsUseCase.invoke().collect { resource ->
                when (resource) {

                    is Resource.Loading -> _connectionState.update {
                        it.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        _connectionState.update { state ->
                            state.copy(
                                isLoading = false,
                                category = resource.data.map {
                                    it.category
                                }.distinct()
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateErrorMessage(resource.errorMessage)
                    }

                }
            }
        }
    }


    private fun updateErrorMessage(message: String?) {
        _connectionState.update { currentState -> currentState.copy(errorMessage = message) }
    }

}