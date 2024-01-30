package com.example.localremot.domain.usecase.remote

import com.example.localremot.domain.repository.remote.ConnectionsRepository
import javax.inject.Inject

class GetConnectionsUseCase @Inject constructor(
    private val connectionsRepository: ConnectionsRepository
) {
    suspend operator fun invoke() = connectionsRepository.getConnections()
}