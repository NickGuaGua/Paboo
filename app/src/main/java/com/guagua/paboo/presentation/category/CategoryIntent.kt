package com.guagua.paboo.presentation.category

import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.model.Category
import com.guagua.paboo.presentation.base.ScreenIntent

sealed class CategoryIntent : ScreenIntent {
    data class CategoryChanged(val category: Category) : CategoryIntent()
    data class ClickArticle(val article: Article) : CategoryIntent()
}