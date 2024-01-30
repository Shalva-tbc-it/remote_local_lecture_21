package com.example.localremot.domain.usecase.local

import com.example.localremot.domain.model.GetConnection
import com.example.localremot.domain.repository.local.ConnectionRepository
import javax.inject.Inject

class InsertItemDbUseCase @Inject constructor(
    private val connectionRepository: ConnectionRepository
) {

    suspend operator fun invoke(getConnection: List<GetConnection>) = connectionRepository.insertItem(getConnection)

}