package io.github.tuguzt.pcbuilder.presentation.view.root.main.builds

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.presentation.view.navigation.BuildScreenDestinations.*
import io.github.tuguzt.pcbuilder.presentation.view.navigation.MainScreenDestinations
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.builds.BuildsViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.builds.EditBuildViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components.ComponentsViewModel

/**
 * Application screen which represents *Builds* main application destination.
 */
@Composable
fun BuildsScreen(
    mainViewModel: MainViewModel,
    componentsViewModel: ComponentsViewModel,
    buildsViewModel: BuildsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    LaunchedEffect(currentRoute) {
        currentRoute ?: return@LaunchedEffect
        val currentDestination = when {
            currentRoute == BuildList.route -> MainScreenDestinations.Builds
            currentRoute == AddBuild.route -> AddBuild
            EditBuild.route in currentRoute -> EditBuild
            CompatibilityBuild.route in currentRoute -> CompatibilityBuild
            BuildDetails.route in currentRoute -> BuildDetails
            else -> return@LaunchedEffect
        }
        mainViewModel.updateCurrentDestination(currentDestination)
        mainViewModel.updateOnNavigateUpAction(navController::navigateUp)
    }

    NavHost(navController = navController, startDestination = BuildList.route) {
        composable(BuildList.route) {
            BuildListScreen(
                mainViewModel = mainViewModel,
                buildsViewModel = buildsViewModel,
                snackbarHostState = snackbarHostState,
                navController = navController,
            )
        }
        composable(AddBuild.route) {
            AddBuildScreen(
                mainViewModel = mainViewModel,
                componentsViewModel = componentsViewModel,
                onAdd = {
                    buildsViewModel.saveBuild(it)
                    navController.popBackStack()
                },
            )
        }
        composable(
            route = "${BuildDetails.route}/{buildId}",
            arguments = listOf(
                navArgument(name = "buildId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("buildId")
                ?.let { NanoId(it) } ?: return@composable

            SideEffect {
                mainViewModel.updateOnBuildEditAction {
                    navController.navigate("${EditBuild.route}/${id}")
                }
                mainViewModel.updateOnBuildCompatibilityAction {
                    navController.navigate("${CompatibilityBuild.route}/${id}")
                }
            }
            BuildDetailsScreen(
                buildId = id,
                mainViewModel = mainViewModel,
                buildsViewModel = buildsViewModel,
                snackbarHostState = snackbarHostState,
            )
        }
        composable(
            route = "${EditBuild.route}/{buildId}",
            arguments = listOf(
                navArgument(name = "buildId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("buildId")
                ?.let { NanoId(it) } ?: return@composable

            val editBuildViewModel: EditBuildViewModel = hiltViewModel()
            SideEffect {
                val prevBuildData = buildsViewModel.uiState.builds.first { it.id == id }
                editBuildViewModel.updatePrevBuildData(prevBuildData)
            }

            EditBuildScreen(
                onEdit = {
                    buildsViewModel.saveBuild(it)
                    navController.popBackStack()
                },
                mainViewModel = mainViewModel,
                componentsViewModel = componentsViewModel,
                editBuildViewModel = editBuildViewModel,
            )
        }
        composable(
            route = "${CompatibilityBuild.route}/{buildId}",
            arguments = listOf(
                navArgument(name = "buildId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("buildId")
                ?.let { NanoId(it) } ?: return@composable

            // todo
        }
    }
}
