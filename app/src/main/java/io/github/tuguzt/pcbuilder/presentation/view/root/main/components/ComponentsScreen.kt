package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.navigation.ComponentScreenDestinations.*
import io.github.tuguzt.pcbuilder.presentation.view.navigation.MainScreenDestinations
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsMessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.RemoteComponentsMessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.RemoteSearchComponentsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Application screen which represents *Components* main application destination.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComponentsScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    componentsViewModel: ComponentsViewModel = hiltViewModel(),
    remoteSearchViewModel: RemoteSearchComponentsViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    LaunchedEffect(currentRoute) {
        currentRoute ?: return@LaunchedEffect
        val currentDestination = when {
            currentRoute == ComponentList.route -> MainScreenDestinations.Components
            currentRoute == RemoteSearchComponent.route -> RemoteSearchComponent
            ComponentDetails.route in currentRoute -> ComponentDetails
            else -> return@LaunchedEffect
        }
        mainViewModel.updateCurrentDestination(currentDestination)
        mainViewModel.updateOnNavigateUpAction(navController::navigateUp)
    }

    val appName = stringResource(R.string.app_name)

    NavHost(navController = navController, startDestination = ComponentList.route) {
        composable(ComponentList.route) {
            val listState = rememberLazyListState()
            val context = LocalContext.current

            SideEffect { mainViewModel.updateTitle(appName) }

            DismissComponentList(
                modifier = Modifier.fillMaxSize(),
                components = componentsViewModel.uiState.components,
                onComponentClick = {
                    navController.navigate("${ComponentDetails.route}/${it.id}")
                },
                onComponentDismiss = {
                    componentsViewModel.deleteComponent(it)
                },
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
                    .distinctUntilChanged()
                    .collect {
                        mainViewModel.updateFilled(isFilled = it > 0)
                    }
            }
        }
        composable(RemoteSearchComponent.route) {
            val listState = rememberLazyListState()
            val context = LocalContext.current

            SideEffect { mainViewModel.updateTitle(appName) }

            ComponentList(
                modifier = Modifier.fillMaxSize(),
                components = remoteSearchViewModel.uiState.components,
                onComponentClick = { /* TODO */ },
                isRefreshing = remoteSearchViewModel.uiState.isUpdating,
                onRefresh = { remoteSearchViewModel.updateComponents() },
                lazyListState = listState,
            )

            remoteSearchViewModel.uiState.userMessages.firstOrNull()?.let { message ->
                LaunchedEffect(message) {
                    snackbarHostState.showSnackbar(
                        message = when (message.kind) {
                            RemoteComponentsMessageKind.UnknownError -> {
                                context.getString(R.string.unknown_error)
                            }
                        },
                        actionLabel = context.getString(R.string.dismiss),
                    )
                    remoteSearchViewModel.userMessageShown(message.id)
                }
            }
            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemScrollOffset }
                    .distinctUntilChanged()
                    .collect {
                        mainViewModel.updateFilled(isFilled = it > 0)
                    }
            }
        }
        dialog(
            route = AddComponent.route,
            dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            AddComponentDialog(
                modifier = Modifier.fillMaxSize(),
                onAddComponent = { component ->
                    scope.launch {
                        componentsViewModel.addComponent(component)
                        navController.popBackStack()
                    }
                },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = "${ComponentDetails.route}/{componentId}",
            arguments = listOf(
                navArgument(name = "componentId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("componentId") ?: return@composable
            val nanoId = NanoId(id)
            val component = remember(nanoId) {
                componentsViewModel.uiState.components.first { it.id == nanoId }
            }

            SideEffect { mainViewModel.updateTitle(component.name) }
            ComponentDetailsScreen(component)
        }
    }
}

fun ComponentsMessageKind.message(context: Context): String = when (this) {
    ComponentsMessageKind.ComponentAdded -> context.getString(R.string.component_added)
    ComponentsMessageKind.ComponentDeleted -> context.getString(R.string.component_deleted)
    else -> context.getString(R.string.unknown_error)
}
