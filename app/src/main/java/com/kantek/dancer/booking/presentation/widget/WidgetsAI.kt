package com.kantek.dancer.booking.presentation.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.kantek.dancer.booking.presentation.theme.Colors
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppLazyVerticalGrid(
    items: List<T>,
    keyItem: ((T) -> Any)? = null,
    columns: GridCells = GridCells.Fixed(2),
    contentPadding: PaddingValues = PaddingValues(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(0.dp),
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    isIndicatorRefreshing: Boolean = isRefreshing,
    onLoadMore: (() -> Unit)? = null,
    onRefresh: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Colors.DarkFF0A050A,
    pullRefreshContentColor: Color = Colors.Primary,
    pullRefreshContainerColor: Color = Colors.White,
    itemContent: @Composable (T, Int, Boolean) -> Unit
) {
    val gridState = rememberLazyGridState()
    val pullToRefreshState = rememberPullToRefreshState()
    var isProgrammaticRefresh by remember { mutableStateOf(false) }

    LaunchedEffect(gridState, items, isLoading, isRefreshing) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .map { it ?: -1 }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (
                    items.isNotEmpty() &&
                    lastVisibleIndex >= 0 &&
                    lastVisibleIndex >= items.lastIndex &&
                    !isLoading &&
                    !isRefreshing
                ) {
                    onLoadMore?.invoke()
                }
            }
    }

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing && !isProgrammaticRefresh) {
            onRefresh?.invoke()
        }
    }

    LaunchedEffect(isIndicatorRefreshing) {
        if (isIndicatorRefreshing && !pullToRefreshState.isRefreshing) {
            isProgrammaticRefresh = true
            pullToRefreshState.startRefresh()
        }
    }

    LaunchedEffect(pullToRefreshState.isRefreshing, isIndicatorRefreshing) {
        if (pullToRefreshState.isRefreshing && !isIndicatorRefreshing) {
            pullToRefreshState.endRefresh()
            isProgrammaticRefresh = false
        }
    }

    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        Box(
            modifier = if (onRefresh != null) Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
            else Modifier
        ) {
            LazyVerticalGrid(
                columns = columns,
                state = gridState,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement
            ) {
                itemsIndexed(
                    items,
                    key = { _, item -> keyItem?.invoke(item) ?: item.hashCode() }
                ) { index, item ->
                    itemContent(item, index, index == items.lastIndex)
                }
            }
            if (onRefresh != null) {
                PullToRefreshContainer(
                    state = pullToRefreshState,
                    contentColor = pullRefreshContentColor,
                    containerColor = pullRefreshContainerColor,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

