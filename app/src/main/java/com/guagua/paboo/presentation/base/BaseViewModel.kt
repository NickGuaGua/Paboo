package com.guagua.paboo.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<S: ScreenState, I: ScreenIntent> : ViewModel(), MviViewModel<S, I> {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(handler) { block() }
    }

    protected open fun handleException(e: Throwable) {
        Timber.d(e.message)
    }
}