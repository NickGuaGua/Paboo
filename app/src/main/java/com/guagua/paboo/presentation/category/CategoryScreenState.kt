package com.guagua.paboo.presentation.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.guagua.paboo.presentation.base.BaseComposableState
import com.guagua.paboo.presentation.base.MviViewModel
import com.guagua.paboo.presentation.composition.LocalAppNavigator
import com.guagua.paboo.presentation.navigation.AppNavigator
import kotlinx.coroutines.CoroutineScope
import java.text.SimpleDateFormat

@OptIn(ExperimentalPagerApi::class)
class CategoryScreenState(
    private val viewModel: MviViewModel<CategoryState, CategoryIntent>,
    scope: CoroutineScope,
    navigator: AppNavigator,
    val lifecycle: Lifecycle,
    val pagerState: PagerState,
) : BaseComposableState<CategoryState, CategoryIntent>(viewModel, scope, navigator) {

    val currentPage get() =  pagerState.currentPage

    fun scrollToPage(page: Int) {
        launch { pagerState.scrollToPage(page) }
    }

    fun handleEvent(event: CategoryEvent) = when(event) {
        is CategoryEvent.Navigation -> {
            navigate(event.screen)
        }
    }.also { viewModel.eventConsumed() }

    companion object {
        @Composable
        fun remember(
            viewModel: MviViewModel<CategoryState, CategoryIntent>,
            scope: CoroutineScope = rememberCoroutineScope(),
            navigator: AppNavigator = LocalAppNavigator.current,
            lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
            pagerState: PagerState = rememberPagerState(),
        ) = CategoryScreenState(viewModel, scope, navigator, lifecycle, pagerState)
    }
}