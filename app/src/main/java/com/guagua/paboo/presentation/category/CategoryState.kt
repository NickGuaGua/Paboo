package com.guagua.paboo.presentation.category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.model.Category
import com.guagua.paboo.presentation.base.ScreenState
import com.guagua.paboo.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow


data class CategoryState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val categories: List<Category>,
    val categoryFlowMap: Map<Category, Flow<PagingData<Article>>> = mapOf(),
    val event: CategoryEvent? = null
): ScreenState

sealed class CategoryEvent {
    data class Navigation(val screen: Screen): CategoryEvent()
}