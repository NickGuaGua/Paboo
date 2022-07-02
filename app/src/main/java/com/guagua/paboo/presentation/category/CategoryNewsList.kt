package com.guagua.paboo.presentation.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.model.Category
import com.guagua.paboo.presentation.widget.LazyPagingColumn
import com.guagua.paboo.presentation.widget.NewsCard

@Composable
fun CategoryNewsList(viewModel: CategoryViewModel, category: Category, onArticleClick: (Article) -> Unit) {
    val state by viewModel.state.collectAsState()
    val pagingItems = state.categoryFlowMap[category]?.collectAsLazyPagingItems()

    LaunchedEffect(LocalLifecycleOwner.current) {
        viewModel.getTopHeadlines(category)
    }

    pagingItems?.let {
        LazyPagingColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            pagingItems = it
        ) {
            NewsCard(article = it, onArticleClick)
        }
    }
}