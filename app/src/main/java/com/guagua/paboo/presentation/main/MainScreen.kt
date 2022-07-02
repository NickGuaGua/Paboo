package com.guagua.paboo.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.guagua.paboo.data.PabooTab
import com.guagua.paboo.presentation.category.CategoryScreen
import com.guagua.paboo.presentation.category.CategoryViewModel
import com.guagua.paboo.presentation.home.HomeScreen
import com.guagua.paboo.presentation.home.HomeViewModel
import com.guagua.paboo.presentation.theme.AppColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(tabs: Array<PabooTab> = PabooTab.values()) {
    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setStatusBarColor(AppColor.BrandGray.copy(alpha = 0.6f), darkIcons = true)
            systemUiController.setNavigationBarColor(AppColor.BrandRed)
        }

        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                count = tabs.size,
                state = pagerState,
                key = { tabs[it] },
                userScrollEnabled = false
            ) {
                when (it) {
                    PabooTab.HOME.ordinal -> HomeScreen(viewModel = hiltViewModel<HomeViewModel>())
                    PabooTab.CATEGORY.ordinal -> CategoryScreen(viewModel = hiltViewModel<CategoryViewModel>())
                }
            }

            TabRow(
                modifier = Modifier
                    .navigationBarsPadding()
                    .wrapContentHeight(),
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = AppColor.BrandRed,
                indicator = {},
                divider = {},
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        modifier = Modifier.wrapContentHeight(),
                        selected = index == pagerState.currentPage,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(tab.ordinal) }
                        },
                        selectedContentColor = AppColor.BrandWhite,
                        unselectedContentColor = AppColor.BrandGray,
                    ) {
                        val isSelected = index == pagerState.currentPage

                        Spacer(modifier = Modifier.height(8.dp))

                        val iconResId = if (isSelected) tab.selectedIconId else tab.unselectedIconId
                        Icon(
                            painter = rememberAsyncImagePainter(model = iconResId),
                            contentDescription = "tab icon"
                        )

                        Text(
                            text = stringResource(id = tab.textResId),
                            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            }
        }
    }
}