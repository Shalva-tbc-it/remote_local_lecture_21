package com.example.localremot.domain.usecase.local

import com.example.localremot.data.local.repository.DbConnectionDataSource
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val dbConnectionDataSource: DbConnectionDataSource
) {
    suspend operator fun invoke(category: String) = dbConnectionDataSource.getCategory(category = category)

}