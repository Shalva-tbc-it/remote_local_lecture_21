package com.example.localremot.domain.usecase.local

import com.example.localremot.domain.repository.local.ConnectionRepository
import javax.inject.Inject

class GetConnectionDbUseCase @Inject constructor(
    private val connectionRepository: ConnectionRepository
) {
    suspend operator fun invoke() = connectionRepository.getAll()
}