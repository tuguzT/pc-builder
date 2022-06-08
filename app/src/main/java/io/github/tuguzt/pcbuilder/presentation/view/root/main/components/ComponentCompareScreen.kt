package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.navigation.ComponentScreenDestinations
import io.github.tuguzt.pcbuilder.presentation.view.navigation.ComponentScreenDestinations.ChooseComponent
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.CompareComponentsMessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentToChoose
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsCompareViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun ComponentCompareScreen(
    mainViewModel: MainViewModel,
    componentsCompareViewModel: ComponentsCompareViewModel,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    val context = LocalContext.current
    val title = ComponentScreenDestinations.CompareComponents.description
    SideEffect {
        mainViewModel.updateTitle(title)
    }

    val scrollState = rememberScrollState()
    ComponentCompare(
        firstComponent = componentsCompareViewModel.uiState.firstComponent,
        secondComponent = componentsCompareViewModel.uiState.secondComponent,
        scrollState = scrollState,
        onFirstComponentCleared = {
            componentsCompareViewModel.updateFirstComponent(component = null)
        },
        onSecondComponentCleared = {
            componentsCompareViewModel.updateSecondComponent(component = null)
        },
        onFirstComponentChoose = {
            navController.navigate("${ChooseComponent.route}/${ComponentToChoose.First}")
        },
        onSecondComponentChoose = {
            navController.navigate("${ChooseComponent.route}/${ComponentToChoose.Second}")
        },
    )

    componentsCompareViewModel.uiState.userMessages.firstOrNull()?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(
                message = when (message.kind) {
                    CompareComponentsMessageKind.DifferentTypes -> context.getString(R.string.choose_one_type)
                    CompareComponentsMessageKind.EqualComponents -> context.getString(R.string.equal_components)
                },
                actionLabel = context.getString(R.string.dismiss),
            )
            componentsCompareViewModel.userMessageShown(message.id)
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
