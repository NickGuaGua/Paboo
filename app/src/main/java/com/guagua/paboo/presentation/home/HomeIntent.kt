package com.guagua.paboo.presentation.home

import com.guagua.paboo.data.model.Article
import com.guagua.paboo.presentation.base.ScreenIntent

sealed class HomeIntent : ScreenIntent {
    object Launch : HomeIntent()
    data class ClickArticle(val article: Article) : HomeIntent()
}