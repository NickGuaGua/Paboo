package com.guagua.paboo.presentation.category

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.model.Category
import com.guagua.paboo.data.model.Country
import com.guagua.paboo.domain.GetTopHeadlinesUseCase
import com.guagua.paboo.presentation.base.BaseViewModel
import com.guagua.paboo.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : BaseViewModel<CategoryState, CategoryIntent>() {

    private val categories = mutableListOf<Category>().apply {
        addAll(Category.values().filter { it != Category.GENERAL })
    }
    private val categoryFlowMap = mutableMapOf<Category, Flow<PagingData<Article>>>()

    private val _state: MutableStateFlow<CategoryState> =
        MutableStateFlow(CategoryState(categories = categories))
    override val state = _state.asStateFlow()

    override fun handleIntent(intentFlow: Flow<CategoryIntent>) {
        launch {
            intentFlow.collectLatest { intent ->
                when (intent) {
                    is CategoryIntent.CategoryChanged -> {
                        getTopHeadlines(intent.category)
                    }
                    is CategoryIntent.ClickArticle -> {
                        _state.update {
                            it.copy(event = CategoryEvent.Navigation(
                                Screen.NewsDetail(intent.article.url)
                            ))
                        }
                    }
                }
            }
        }
    }

    override fun eventConsumed() {
        _state.update { it.copy(event = null) }
    }

    fun getTopHeadlines(category: Category) = launch {
        categoryFlowMap.getOrPut(category) {
            getTopHeadlinesUseCase(Country.TW, category).cachedIn(viewModelScope)
        }
        _state.update { it.copy(categoryFlowMap = categoryFlowMap) }
    }

    override fun handleException(e: Throwable) {
        super.handleException(e)
        _state.update { it.copy(isLoading = false, error = e) }
    }
}