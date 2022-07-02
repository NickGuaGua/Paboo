package com.guagua.paboo.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import com.guagua.paboo.presentation.base.BaseComposableState
import com.guagua.paboo.presentation.base.MviViewModel
import com.guagua.paboo.presentation.composition.LocalAppNavigator
import com.guagua.paboo.presentation.navigation.AppNavigator
import kotlinx.coroutines.CoroutineScope
import java.text.SimpleDateFormat

class HomeScreenState(
    private val viewModel: MviViewModel<HomeState, HomeIntent>,
    scope: CoroutineScope,
    navigator: AppNavigator,
    val lifecycle: Lifecycle,
    val dateText: String
) : BaseComposableState<HomeState, HomeIntent>(viewModel, scope, navigator) {

    fun handleEvent(event: HomeEvent) = when(event) {
        is HomeEvent.Navigation -> {
            navigate(event.screen)
        }
    }.also { viewModel.eventConsumed() }

    companion object {
        @Composable
        fun remember(
            viewModel: MviViewModel<HomeState, HomeIntent>,
            scope: CoroutineScope = rememberCoroutineScope(),
            navigator: AppNavigator = LocalAppNavigator.current,
            lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
            dateText: String = remember {
                SimpleDateFormat("EEEE, dd MMMM").format(System.currentTimeMillis())
            },
        ) = HomeScreenState(viewModel, scope, navigator, lifecycle, dateText)
    }
}