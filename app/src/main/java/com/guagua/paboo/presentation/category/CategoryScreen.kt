package com.guagua.paboo.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.data.model.Category
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
import kotlinx.coroutines.launch
import timber.log.Timber

@Preview
@Composable
fun CategoryScreenPreview() = preview {
    CategoryScreen(object : MviViewModel<CategoryState, CategoryIntent> {
        private val categories = Category.values().filter { it != Category.GENERAL }
        override val state: StateFlow<CategoryState> = MutableStateFlow(
            CategoryState(
                isLoading = true,
                categories = categories,
                categoryFlowMap = categories.associateWith { MockUtil.getArticlePagingFlow(10) }
            )
        )

        override fun handleIntent(intentFlow: Flow<CategoryIntent>) {}

        override fun eventConsumed() {}
    })
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CategoryScreen(viewModel: MviViewModel<CategoryState, CategoryIntent>) {
    val screenState = CategoryScreenState.remember(viewModel)
    val state by viewModel.state.collectAsState()
    val categories = state.categories

    state.event?.let {
        screenState.handleEvent(it)
    }

    Column(modifier = Modifier.fillMaxSize().background(AppColor.BrandGray)) {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .statusBarsHeight()
            .background(AppColor.BrandGray))

        ScrollableTabRow(
            selectedTabIndex = screenState.currentPage,
            backgroundColor = AppColor.BrandGray,
            contentColor = AppColor.TextPrimary,
            edgePadding = 4.dp,
            divider = {},
            indicator = {
                TabRowDefaults.Indicator(
                    Modifier
                        .padding(bottom = 4.dp)
                        .tabIndicatorOffset(it[screenState.currentPage])
                        .height(6.dp)
                        .aspectRatio(1f, true)
                        .clip(CircleShape)
                )
            }
        ) {
            categories.forEachIndexed { index, category ->
                CategoryTab(category, screenState.currentPage == index) {
                    screenState.scrollToPage(index)
                }
            }
        }

        HorizontalPager(
            count = categories.size,
            state = screenState.pagerState,
            key = { categories[it] }
        ) { index ->
            val pagingItems = state.categoryFlowMap[categories[index]]?.collectAsLazyPagingItems()

            LaunchedEffect(screenState.currentPage) {
                screenState.sendIntent(CategoryIntent.CategoryChanged(categories[index]))
            }

            pagingItems?.let {
                LazyPagingColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    pagingItems = it
                ) { article ->
                    NewsCard(article = article) {
                        screenState.sendIntent(CategoryIntent.ClickArticle(article))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryTab(category: Category, isSelected: Boolean, onClick: () -> Unit) {
    Tab(
        selected = isSelected,
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            text = category.name.lowercase().replaceFirstChar { it.uppercase() },
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}