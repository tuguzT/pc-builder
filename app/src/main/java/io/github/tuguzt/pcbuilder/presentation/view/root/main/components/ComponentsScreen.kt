package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.navigation.ComponentScreenDestinations.*
import io.github.tuguzt.pcbuilder.presentation.view.navigation.MainScreenDestinations
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.*
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Application screen which represents *Components* main application destination.
 */
@Composable
fun ComponentsScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    componentsViewModel: ComponentsViewModel = hiltViewModel(),
    componentsCompareViewModel: ComponentsCompareViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    LaunchedEffect(currentRoute) {
        currentRoute ?: return@LaunchedEffect
        val currentDestination = when {
            currentRoute == ComponentList.route -> MainScreenDestinations.Components
            currentRoute == SearchComponent.route -> SearchComponent
            CompareComponents.route in currentRoute -> CompareComponents
            currentRoute == Favorites.route -> Favorites
            ComponentDetails.route in currentRoute -> ComponentDetails
            else -> return@LaunchedEffect
        }
        mainViewModel.updateCurrentDestination(currentDestination)
        mainViewModel.updateOnNavigateUpAction(navController::navigateUp)
    }

    NavHost(navController = navController, startDestination = ComponentList.route) {
        composable(ComponentList.route) {
            ComponentListScreen(
                mainViewModel = mainViewModel,
                componentsViewModel = componentsViewModel,
                snackbarHostState = snackbarHostState,
                navController = navController,
            )
        }
        composable(Favorites.route) {
            FavoriteComponentListScreen(
                mainViewModel = mainViewModel,
                componentsViewModel = componentsViewModel,
                snackbarHostState = snackbarHostState,
                navController = navController,
            )
        }
        composable(CompareComponents.route) {
            val context = LocalContext.current
            val title = CompareComponents.description
            SideEffect {
                mainViewModel.updateTitle(title)
            }

            val scrollState = rememberScrollState()
            ComponentCompareScreen(
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
        composable(
            route = "${ChooseComponent.route}/{discriminator}",
            arguments = listOf(
                navArgument(name = "discriminator") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val discriminator = backStackEntry.arguments?.getString("discriminator")
                ?.let { ComponentToChoose.valueOf(it) } ?: return@composable

            val listState = rememberLazyListState()
            val context = LocalContext.current
            val title = ChooseComponent.description
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
        composable(SearchComponent.route) {
            val title = SearchComponent.description
            SideEffect {
                mainViewModel.updateTitle(title)
                mainViewModel.updateFilled(isFilled = false)
            }
            // todo
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

            val title = stringResource(R.string.component_details)
            val scrollState = rememberScrollState()
            SideEffect {
                mainViewModel.updateTitle(title)
            }
            ComponentDetailsScreen(component, scrollState)

            LaunchedEffect(scrollState) {
                snapshotFlow { scrollState.value }
                    .map { it > 0 }
                    .distinctUntilChanged()
                    .collect {
                        mainViewModel.updateFilled(isFilled = it)
                    }
            }
        }
    }
}

fun ComponentsMessageKind.message(context: Context): String = when (this) {
    ComponentsMessageKind.ComponentAdded -> context.getString(R.string.component_added)
    ComponentsMessageKind.ComponentDeleted -> context.getString(R.string.component_deleted)
    else -> context.getString(R.string.unknown_error)
}
