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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.navigation.ComponentScreenDestinations.ComponentDetails
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.favoriteComponents
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun FavoriteComponentListScreen(
    mainViewModel: MainViewModel,
    componentsViewModel: ComponentsViewModel,
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
) {
    println(componentsViewModel.uiState)
    val listState = rememberLazyListState()
    val context = LocalContext.current

    val title = stringResource(R.string.favorite_components)
    SideEffect { mainViewModel.updateTitle(title) }

    ComponentList(
        modifier = Modifier.fillMaxSize(),
        components = componentsViewModel.uiState.favoriteComponents,
        onComponentClick = { component ->
            navController.navigate("${ComponentDetails.route}/${component.id}")
        },
        onFavoriteComponentClick = { component, isFavorite ->
            componentsViewModel.updateFavorites(component, isFavorite)
        },
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
