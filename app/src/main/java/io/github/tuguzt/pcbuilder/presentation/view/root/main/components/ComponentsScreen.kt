package io.github.tuguzt.pcbuilder.presentation.view.root.main.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Application screen which represents *Components* main application destination.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ComponentsScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    componentsViewModel: ComponentsViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    LaunchedEffect(currentRoute) {
        val currentDestination = when {
            currentRoute == ComponentList.route -> MainScreenDestinations.Components
            currentRoute?.let { ComponentDetails.route in it } == true -> ComponentDetails
            else -> return@LaunchedEffect
        }
        mainViewModel.updateCurrentDestination(currentDestination)
        mainViewModel.updateOnNavigateUpAction(navController::navigateUp)
    }

    val appName = stringResource(R.string.app_name)
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(navController = navController, startDestination = ComponentList.route) {
        composable(ComponentList.route) {
            val context = LocalContext.current
            SideEffect { mainViewModel.updateTitle(appName) }

            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        text = { Text(stringResource(R.string.add_component)) },
                        icon = {
                            Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                        },
                        onClick = { navController.navigate(AddComponent.route) },
                    )
                }
            ) { padding ->
                ComponentList(
                    modifier = Modifier.padding(padding),
                    components = componentsViewModel.uiState.components,
                    onComponentClick = {
                        navController.navigate("${ComponentDetails.route}/${it.id}")
                    },
                    onComponentDismiss = {
                        componentsViewModel.deleteComponent(it)
                    }
                )
            }

            componentsViewModel.uiState.userMessages.firstOrNull()?.let { message ->
                LaunchedEffect(message) {
                    snackbarHostState.showSnackbar(
                        message = message.kind.message(context),
                        actionLabel = context.getString(R.string.dismiss),
                    )
                    componentsViewModel.userMessageShown(message.id)
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
}
