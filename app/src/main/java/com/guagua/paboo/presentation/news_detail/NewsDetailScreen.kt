package com.guagua.paboo.presentation.news_detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun NewsDetailScreen(articleUrl: String) {
    val state = rememberWebViewState(articleUrl)
    WebView(modifier = Modifier.statusBarsPadding(), state = state)
}
