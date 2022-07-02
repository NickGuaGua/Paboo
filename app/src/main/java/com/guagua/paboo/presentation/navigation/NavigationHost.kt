package com.guagua.paboo.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.guagua.paboo.presentation.main.MainScreen
import com.guagua.paboo.presentation.news_detail.NewsDetailScreen

@ExperimentalMaterialApi
@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Main.path,
    ) {
        composable(Screen.Main.path) {
            MainScreen()
        }
        composable(
            route = Screen.NewsDetail.path,
            arguments = listOf(
                navArgument(Screen.NewsDetail.KEY_ARTICLE_URL) { type = NavType.StringType }
            )
        ) {
            val url = it.arguments?.getString(Screen.NewsDetail.KEY_ARTICLE_URL) ?: ""
            NewsDetailScreen(url)
        }
    }
}