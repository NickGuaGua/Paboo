package com.guagua.paboo.domain

import com.guagua.paboo.data.model.Category
import com.guagua.paboo.data.model.Country
import com.guagua.paboo.data.repository.PabooRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val repository: PabooRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        country: Country = Country.TW,
        category: Category = Category.GENERAL,
        pageSize: Int = 20,
        page: Int = 1
    ) = withContext(dispatcher) {
        repository.getTopHeadlinesPagingFlow(country, category, null, page, pageSize)
    }
}