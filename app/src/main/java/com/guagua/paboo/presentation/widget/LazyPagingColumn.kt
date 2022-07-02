package com.guagua.paboo.presentation.widget

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.guagua.paboo.R
import com.guagua.paboo.extension.*

@Composable
fun <T : Any> LazyPagingColumn(
    pagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    prepend: @Composable (LazyItemScope.() -> Unit)? = null,
    append: @Composable (LazyItemScope.() -> Unit)? = null,
    content: @Composable LazyItemScope.(T) -> Unit,
) {
    val context = LocalContext.current
    val refreshState = rememberSwipeRefreshState(isRefreshing = pagingItems.isRefreshLoading())
    Box(modifier = modifier) {
        SwipeRefresh(
            state = refreshState,
            onRefresh = { pagingItems.refresh() }
        ) {
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = verticalArrangement,
                contentPadding = contentPadding
            ) {
                prepend?.let {
                    item { it.invoke(this) }
                }
                items(pagingItems) { item ->
                    item?.let { content(it) }
                }
                append?.let {
                    item { it.invoke(this) }
                }
            }

            Loading(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                isVisible = pagingItems.isAppendLoading()
            )

            if (pagingItems.isEmpty()) {
                ErrorContent(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    icon = R.drawable.ic_no_data,
                    text = stringResource(id = R.string.error_no_data)
                )
            }

            pagingItems.getRefreshError()?.apply {
                if (pagingItems.isEmpty()) {
                    ErrorContent(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center),
                        icon = R.drawable.ic_warning,
                        text = stringResource(id = R.string.error_general)
                    )
                } else {
                    Toast.makeText(context, stringResource(id = R.string.error_general), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}