package com.guagua.paboo.presentation.composition

import androidx.compose.runtime.staticCompositionLocalOf
import com.guagua.paboo.presentation.navigation.AppNavigator

val LocalAppNavigator =
    staticCompositionLocalOf<AppNavigator> { error("No Bubble Navigator provided") }