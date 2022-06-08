package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.navigation.ComponentScreenDestinations
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentToChoose
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsCompareViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun ChooseComponentScreen(
    discriminator: ComponentToChoose,
    mainViewModel: MainViewModel,
    componentsViewModel: ComponentsViewModel,
    componentsCompareViewModel: ComponentsCompareViewModel,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val title = ComponentScreenDestinations.ChooseComponent.description
    SideEffect {
        mainViewModel.updateTitle(title)
    }

    ComponentList(
        modifier = Modifier.fillMaxSize(),
        components = componentsViewModel.uiState.components,
        onComponentClick = { component ->
            when (discriminator) {
                ComponentToChoose.First ->
                    componentsCompareViewModel.updateFirstComponent(component)
                ComponentToChoose.Second ->
                    componentsCompareViewModel.updateSecondComponent(component)
            }
            navController.popBackStack()
        },
        onFavoriteComponentClick = { _, _ -> },
        isRefreshing = componentsViewModel.uiState.isUpdating,
        onRefresh = { componentsViewModel.updateComponents() },
        lazyListState = listState,
    )

    componentsViewModel.uiState.userMessages.firstOrNull()?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(
                message = message.kind.message(context),
                actionLabel = context.getString(R.string.dismiss),
            )
            componentsViewModel.userMessageShown(message.id)
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
