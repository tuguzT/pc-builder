package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.navigation.ComponentScreenDestinations.*
import io.github.tuguzt.pcbuilder.presentation.view.navigation.MainScreenDestinations
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentToChoose
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsCompareViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsMessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsViewModel

/**
 * Application screen which represents *Components* main application destination.
 */
@Composable
fun ComponentsScreen(
    mainViewModel: MainViewModel,
    componentsViewModel: ComponentsViewModel = hiltViewModel(),
    componentsCompareViewModel: ComponentsCompareViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
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
            ComponentCompareScreen(
                mainViewModel = mainViewModel,
                componentsCompareViewModel = componentsCompareViewModel,
                snackbarHostState = snackbarHostState,
                navController = navController,
            )
        }
        composable(
            route = "${ChooseComponent.route}/{discriminator}",
            arguments = listOf(
                navArgument(name = "discriminator") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val discriminator = backStackEntry.arguments?.getString("discriminator")
                ?.let { ComponentToChoose.valueOf(it) } ?: return@composable

            ChooseComponentScreen(
                discriminator = discriminator,
                mainViewModel = mainViewModel,
                componentsViewModel = componentsViewModel,
                componentsCompareViewModel = componentsCompareViewModel,
                snackbarHostState = snackbarHostState,
                navController = navController,
            )
        }
        composable(
            route = "${ComponentDetails.route}/{componentId}",
            arguments = listOf(
                navArgument(name = "componentId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("componentId")
                ?.let { NanoId(it) } ?: return@composable

            ComponentDetailsScreen(
                componentId = id,
                mainViewModel = mainViewModel,
                componentsViewModel = componentsViewModel,
            )
        }
        composable(SearchComponent.route) {
            val title = SearchComponent.description
            SideEffect {
                mainViewModel.updateTitle(title)
                mainViewModel.updateFilled(isFilled = false)
            }
            // todo
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.coming_soon),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}

fun ComponentsMessageKind.message(context: Context): String = when (this) {
    ComponentsMessageKind.ComponentAdded -> context.getString(R.string.component_added)
    ComponentsMessageKind.ComponentDeleted -> context.getString(R.string.component_deleted)
    ComponentsMessageKind.UnknownError -> context.getString(R.string.unknown_error)
}
