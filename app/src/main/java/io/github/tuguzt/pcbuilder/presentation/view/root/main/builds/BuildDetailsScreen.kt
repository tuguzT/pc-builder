package io.github.tuguzt.pcbuilder.presentation.view.root.main.builds

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
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
) {
    val build = remember(buildId) {
        buildsViewModel.uiState.builds.first { it.id == buildId }
    }
    val title = stringResource(R.string.build_details)
    val scrollState = rememberScrollState()
    SideEffect {
        mainViewModel.updateTitle(title)
    }
    BuildDetails(build, scrollState)

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }
            .map { it > 0 }
            .distinctUntilChanged()
            .collect {
                mainViewModel.updateFilled(isFilled = it)
            }
    }
}
