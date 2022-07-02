package com.guagua.paboo.presentation.home

import androidx.paging.PagingData
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.presentation.base.ScreenState
import com.guagua.paboo.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow


data class HomeState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val articlesPagingFlow: Flow<PagingData<Article>>? = null,
    val event: HomeEvent? = null
): ScreenState

sealed class HomeEvent {
    data class Navigation(val screen: Screen): HomeEvent()
}