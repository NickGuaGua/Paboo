package com.guagua.paboo.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.guagua.paboo.R
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.util.MockUtil
import com.guagua.paboo.extension.preview
import com.guagua.paboo.presentation.base.MviViewModel
import com.guagua.paboo.presentation.composition.LocalAppNavigator
import com.guagua.paboo.presentation.navigation.Screen
import com.guagua.paboo.presentation.theme.AppColor
import com.guagua.paboo.presentation.widget.LazyPagingColumn
import com.guagua.paboo.presentation.widget.NewsCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.text.SimpleDateFormat

@Preview
@Composable
fun HomeScreenPreview() = preview {
    HomeScreen(object : MviViewModel<HomeState, HomeIntent> {
        override val state: StateFlow<HomeState> = MutableStateFlow(
            HomeState(
                articlesPagingFlow = Pager(PagingConfig(10), null) {
                    object : PagingSource<Int, Article>() {
                        override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null
                        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
                            return LoadResult.Page(MockUtil.getArticles(10), null, null)
                        }
                    }
                }.flow
            )
        )

        override fun handleIntent(intentFlow: Flow<HomeIntent>) {}
        override fun eventConsumed() {}
    })
}

@Composable
fun HomeScreen(viewModel: MviViewModel<HomeState, HomeIntent>) {
    val screenState = HomeScreenState.remember(viewModel = viewModel)
    val state by viewModel.state.collectAsState()
    val articlePagingItems = state.articlesPagingFlow?.collectAsLazyPagingItems()
    val paddingValues = rememberInsetsPaddingValues(
        insets = LocalWindowInsets.current.systemBars,
        additionalTop = 16.dp, additionalBottom = 16.dp,
        additionalStart = 16.dp, additionalEnd = 16.dp
    )

    state.event?.let { screenState.handleEvent(it) }

    LaunchedEffect(screenState.lifecycle, viewModel) {
        screenState.sendIntent(HomeIntent.Launch)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(AppColor.BrandGray)) {
        articlePagingItems?.let { pagingItems ->
            Timber.d("load state: ${pagingItems.loadState}")
            LazyPagingColumn(
                pagingItems = pagingItems,
                modifier = Modifier.matchParentSize(),
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                prepend = {
                    Column {
                        Image(
                            modifier = Modifier
                                .width(150.dp)
                                .height(60.dp),
                            painter = rememberAsyncImagePainter(model = R.drawable.logo_paboo),
                            contentDescription = "logo",
                            contentScale = ContentScale.Crop
                        )
                        Text(text = screenState.dateText, color = Color.Gray)
                    }
                }
            ) {
                NewsCard(it) { article ->
                    screenState.sendIntent(HomeIntent.ClickArticle(article))
                }
            }
        }
    }
}