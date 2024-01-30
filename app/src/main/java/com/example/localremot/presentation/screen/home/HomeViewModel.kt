package com.example.localremot.presentation.screen.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localremot.data.common.Resource
import com.example.localremot.domain.usecase.local.GetConnectionDbUseCase
import com.example.localremot.domain.usecase.local.InsertItemDbUseCase
import com.example.localremot.domain.usecase.remote.GetConnectionsUseCase
import com.example.localremot.presentation.event.ConnectionEvent
import com.example.localremot.presentation.mapper.toDomain
import com.example.localremot.presentation.mapper.toPresentation
import com.example.localremot.presentation.model.Connection
import com.example.localremot.presentation.state.ConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getConnectionsUseCase: GetConnectionsUseCase,
    private val getConnectionDbUseCase: GetConnectionDbUseCase,
    private val insertItemDbUseCase: InsertItemDbUseCase
): ViewModel() {

    private val _connectionState = MutableStateFlow(ConnectionState())
    val connectionState: SharedFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _tasks = MutableStateFlow<List<Connection>>(emptyList())
    val tasks: StateFlow<List<Connection>> get() = _tasks

    fun onEvent(event: ConnectionEvent) {
        when (event) {
            is ConnectionEvent.FetchConnections -> fetchConnections()
            is ConnectionEvent.FetchConnectionsFromDb -> getItemsFromDb()
            is ConnectionEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun addTask(connection: List<Connection>) {
        viewModelScope.launch {
            if (connection.isNotEmpty()) {
                insertItemDbUseCase.invoke(connection.map {
                    it.toDomain()
                })
            }
        }
    }

    private fun getItemsFromDb() {

        viewModelScope.launch {
            getConnectionDbUseCase.invoke().collect {
                _tasks.value = it.map { data ->
                    data.toPresentation()
                }
            }
        }
    }

    private fun fetchConnections() {
        viewModelScope.launch {
            getConnectionsUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _connectionState.update { currentState ->
                        currentState.copy(
                            isLoading = resource.loading
                        )
                    }

                    is Resource.Success -> {
                        _connectionState.update { currentState -> currentState.copy(connections = resource.data.map { it.toPresentation() }) }
                        if (resource.data.isNotEmpty()) {
                            addTask(resource.data.map {
                                it.toPresentation()
                            })
                        }
                    }

                    is Resource.Error -> updateErrorMessage(message = resource.errorMessage)
                }
            }
        }
    }


    private fun updateErrorMessage(message: String?) {
        _connectionState.update { currentState -> currentState.copy(errorMessage = message) }
    }



    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            val networkCapabilities = it.activeNetwork ?: return false
            val actNw = it.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        return result
    }

}