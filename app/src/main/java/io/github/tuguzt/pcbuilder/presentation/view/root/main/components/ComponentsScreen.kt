package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import android.content.Context
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
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
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsMessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Application screen which represents *Components* main application destination.
 */
@Composable
fun ComponentsScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    componentsViewModel: ComponentsViewModel = hiltViewModel(),
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
            currentRoute == Favorites.route -> Favorites
            ComponentDetails.route in currentRoute -> ComponentDetails
            else -> return@LaunchedEffect
        }
        mainViewModel.updateCurrentDestination(currentDestination)
        mainViewModel.updateOnNavigateUpAction(navController::navigateUp)
    }

    val appName = stringResource(R.string.app_name)

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
        composable(SearchComponent.route) {
            SideEffect {
                mainViewModel.updateTitle(appName)
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
