package com.guagua.paboo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import com.guagua.paboo.data.PabooTab
import com.guagua.paboo.presentation.category.CategoryScreen
import com.guagua.paboo.presentation.composition.LocalAppNavigator
import com.guagua.paboo.presentation.home.HomeScreen
import com.guagua.paboo.presentation.navigation.AppNavigatorImpl
import com.guagua.paboo.presentation.navigation.NavigationHost
import com.guagua.paboo.presentation.navigation.Screen
import com.guagua.paboo.presentation.theme.AppColor
import com.guagua.paboo.presentation.theme.PabooTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var gson: Gson

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            val navigator = remember(navController) { AppNavigatorImpl(navController, gson) }

            ProvideWindowInsets {
                CompositionLocalProvider(
                    LocalAppNavigator provides navigator
                ) {
                    PabooTheme {
                        NavigationHost(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}