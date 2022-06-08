package io.github.tuguzt.pcbuilder.presentation.view.root.main.builds

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.builds.BuildsViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun BuildDetailsScreen(
    buildId: NanoId,
    mainViewModel: MainViewModel,
    buildsViewModel: BuildsViewModel,
    snackbarHostState: SnackbarHostState,
) {
    val context = LocalContext.current
    val build = remember(buildsViewModel.uiState) {
        buildsViewModel.uiState.builds.first { it.id == buildId }
    }
    val title = stringResource(R.string.build_details)
    val scrollState = rememberScrollState()
    SideEffect {
        mainViewModel.updateTitle(title)
    }
    BuildDetails(build, scrollState)

    buildsViewModel.uiState.userMessages.firstOrNull()?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(
                message = message.kind.message(context),
                actionLabel = context.getString(R.string.dismiss),
            )
            buildsViewModel.userMessageShown(message.id)
        }
    }
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }
            .map { it > 0 }
            .distinctUntilChanged()
            .collect {
                mainViewModel.updateFilled(isFilled = it)
            }
    }
}
