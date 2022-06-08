package io.github.tuguzt.pcbuilder.presentation.view.root.main.builds

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData

@Composable
fun BuildList(
    builds: List<BuildData>,
    onBuildClick: (BuildData) -> Unit,
    onBuildDelete: (BuildData) -> Unit,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    lazyListState: LazyListState = rememberLazyListState(),
) {
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing), onRefresh = onRefresh) {
        LazyColumn(
            modifier = modifier,
            state = lazyListState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(builds, key = { "${it.id}" }) { build ->
                BuildItem(
                    build = build,
                    onClick = { onBuildClick(build) },
                    onClose = { onBuildDelete(build) },
                )
            }
        }
    }
}
