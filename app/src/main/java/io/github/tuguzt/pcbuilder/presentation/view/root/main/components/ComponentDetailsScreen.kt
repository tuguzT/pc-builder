package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun ComponentDetailsScreen(
    componentId: NanoId,
    mainViewModel: MainViewModel,
    componentsViewModel: ComponentsViewModel,
) {
    val component = remember(componentId) {
        componentsViewModel.uiState.components.first { it.id == componentId }
    }
    val title = stringResource(R.string.component_details)
    val scrollState = rememberScrollState()
    SideEffect {
        mainViewModel.updateTitle(title)
    }
    ComponentDetails(component, scrollState)

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }
            .map { it > 0 }
            .distinctUntilChanged()
            .collect {
                mainViewModel.updateFilled(isFilled = it)
            }
    }
}
