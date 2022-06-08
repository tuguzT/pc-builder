package io.github.tuguzt.pcbuilder.presentation.view.root.main.builds

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.navigation.BuildScreenDestinations.BuildDetails
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.builds.BuildsMessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.builds.BuildsViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun BuildListScreen(
    mainViewModel: MainViewModel,
    buildsViewModel: BuildsViewModel,
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current

    val appName = stringResource(R.string.app_name)
    SideEffect { mainViewModel.updateTitle(appName) }

    BuildList(
        modifier = Modifier.fillMaxSize(),
        builds = buildsViewModel.uiState.builds,
        onBuildClick = { build ->
            navController.navigate("${BuildDetails.route}/${build.id}")
        },
        onBuildDelete = buildsViewModel::deleteBuild,
        isRefreshing = buildsViewModel.uiState.isUpdating,
        onRefresh = buildsViewModel::updateBuilds,
        lazyListState = listState,
    )

    buildsViewModel.uiState.userMessages.firstOrNull()?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(
                message = message.kind.message(context),
                actionLabel = context.getString(R.string.dismiss),
            )
            buildsViewModel.userMessageShown(message.id)
        }
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .map { it > 0 }
            .distinctUntilChanged()
            .collect {
                mainViewModel.updateFilled(isFilled = it)
            }
    }
}

fun BuildsMessageKind.message(context: Context): String = when (this) {
    BuildsMessageKind.BuildAdded -> context.getString(R.string.build_added)
    BuildsMessageKind.BuildDeleted -> context.getString(R.string.build_deleted)
    BuildsMessageKind.UnknownError -> context.getString(R.string.unknown_error)
}
