package com.guagua.paboo.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.paging.PagingFlow
import com.guagua.paboo.domain.GetTopHeadlinesUseCase
import com.guagua.paboo.presentation.base.BaseViewModel
import com.guagua.paboo.presentation.base.MviViewModel
import com.guagua.paboo.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : BaseViewModel<HomeState, HomeIntent>() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    override val state = _state.asStateFlow()

    private lateinit var articlePagingFlow: PagingFlow<Article>

    override fun handleIntent(intentFlow: Flow<HomeIntent>) {
        launch {
            intentFlow.collectLatest { intent ->
                when (intent) {
                    is HomeIntent.ClickArticle -> {
                        _state.update {
                            it.copy(event = HomeEvent.Navigation(
                                Screen.NewsDetail(intent.article.url)
                            ))
                        }
                    }
                    HomeIntent.Launch -> {
                        articlePagingFlow = getTopHeadlinesUseCase()
                        _state.update { it.copy(isLoading = false, articlesPagingFlow = articlePagingFlow.flow) }
                    }
                    HomeIntent.Refresh -> {
                        articlePagingFlow.refresh()
                    }
                }
            }
        }
    }

    override fun eventConsumed() {
        _state.update { it.copy(event = null) }
    }

    override fun handleException(e: Throwable) {
        super.handleException(e)
        _state.update { it.copy(isLoading = false, error = e) }
    }
}