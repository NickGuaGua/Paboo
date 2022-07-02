package com.guagua.paboo.presentation.base

import com.guagua.paboo.presentation.navigation.AppNavigator
import com.guagua.paboo.presentation.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface ScreenState
interface ScreenIntent

interface MviViewModel<S: ScreenState, I: ScreenIntent> {
    val state: StateFlow<S>
    fun handleIntent(intentFlow: Flow<I>)
    fun eventConsumed()
}

abstract class BaseComposableState<S: ScreenState, I: ScreenIntent>(
    viewModel: MviViewModel<S, I>,
    private val scope: CoroutineScope,
    private val navigator: AppNavigator
) {
    private val intents = MutableSharedFlow<I>()

    init {
        viewModel.handleIntent(intents)
    }

    fun sendIntent(intent: I) {
        launch { intents.emit(intent) }
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        scope.launch { block() }
    }

    protected fun navigate(screen: Screen) {
        navigator.navigate(screen)
    }
}