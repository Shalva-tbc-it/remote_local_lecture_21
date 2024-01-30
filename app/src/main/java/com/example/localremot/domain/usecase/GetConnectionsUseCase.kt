package com.example.localremot.domain.usecase

import com.example.localremot.domain.repository.ConnectionsRepository
import javax.inject.Inject

class GetConnectionsUseCase @Inject constructor(
    private val connectionsRepository: ConnectionsRepository
) {
    suspend operator fun invoke() = connectionsRepository.getConnections()
}