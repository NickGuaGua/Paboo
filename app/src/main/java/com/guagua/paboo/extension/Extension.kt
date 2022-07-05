package com.guagua.paboo.extension

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.gson.Gson
import com.guagua.paboo.R
import com.guagua.paboo.data.PabooException
import com.guagua.paboo.presentation.composition.LocalAppNavigator
import com.guagua.paboo.presentation.navigation.AppNavigatorImpl

fun LazyPagingItems<*>.isRefreshLoading() = loadState.refresh is LoadState.Loading

fun LazyPagingItems<*>.isRefreshError() = loadState.refresh is LoadState.Error

fun LazyPagingItems<*>.getRefreshError() = (loadState.refresh as? LoadState.Error)?.error

fun LazyPagingItems<*>.isAppendLoading() = loadState.append is LoadState.Loading

fun LazyPagingItems<*>.isAppendEnd() = loadState.append.endOfPaginationReached

fun LazyPagingItems<*>.isAppendError() = loadState.append is LoadState.Error

fun LazyPagingItems<*>.getAppendError() = (loadState.append as? LoadState.Error)?.error

fun LazyPagingItems<*>.isEmpty() = loadState.refresh.endOfPaginationReached && itemCount == 0

fun LazyPagingItems<*>.isSourceRefreshLoading() = loadState.source.refresh is LoadState.Loading

@Composable
fun preview(block: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppNavigator provides AppNavigatorImpl(rememberNavController(), Gson())
    ) {
       block()
    }
}

fun Throwable.getErrorMessage(context: Context): String {
    val e = PabooException.from(this)
    return if (e.code == null) {
        context.getString(R.string.error_general)
    } else "${e.message} (${e.code})."
}