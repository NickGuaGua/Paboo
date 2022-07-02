package com.guagua.paboo.presentation.news_detail

import com.guagua.paboo.presentation.base.BaseViewModel
import com.guagua.paboo.presentation.base.ScreenIntent
import com.guagua.paboo.presentation.base.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(): BaseViewModel<ScreenState, ScreenIntent>() {
    override val state: StateFlow<ScreenState> = MutableStateFlow(object : ScreenState {}).asStateFlow()

    override fun handleIntent(intentFlow: Flow<ScreenIntent>) {

    }

    override fun eventConsumed() {

    }
}