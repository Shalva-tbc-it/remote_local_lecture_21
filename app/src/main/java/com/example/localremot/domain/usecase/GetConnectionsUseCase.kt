package com.example.localremot.domain.usecase

import com.example.localremot.domain.repository.GetConnectionsRepository
import javax.inject.Inject

class GetConnectionsUseCase @Inject constructor(
    private val getConnectionsRepository: GetConnectionsRepository
) {
    suspend operator fun invoke() = getConnectionsRepository.getItems()
}