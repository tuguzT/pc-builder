package io.github.tuguzt.pcbuilder.view.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.view.collectAsStateLifecycleAware
import io.github.tuguzt.pcbuilder.view.navigation.ComponentScreenDestinations.*
import io.github.tuguzt.pcbuilder.viewmodel.components.ComponentsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Application screen which represents *Components* main application destination.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ComponentsScreen(
    onTitleChanged: (String) -> Unit,
    componentsViewModel: ComponentsViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
) {
    val appName = stringResource(R.string.app_name)

    val components by componentsViewModel.components
        .collectAsStateLifecycleAware(listOf(), scope.coroutineContext)
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(navController = navController, startDestination = ComponentList.route) {
        composable(ComponentList.route) {
            SideEffect { onTitleChanged(appName) }

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
                    components = components,
                    onComponentClick = {
                        navController.navigate("${ComponentDetails.route}/${it.id}")
                    },
                )
            }
        }
        dialog(
            route = AddComponent.route,
            dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            val componentAddedMessage = stringResource(R.string.component_added)
            val dismissText = stringResource(R.string.dismiss)
            AddComponentDialog(
                modifier = Modifier.fillMaxSize(),
                onAddComponent = { component ->
                    scope.launch {
                        componentsViewModel.addComponent(component)
                        withContext(Dispatchers.Main) {
                            navController.popBackStack()
                        }
                        snackbarHostState.showSnackbar(
                            message = componentAddedMessage,
                            actionLabel = dismissText,
                        )
                    }
                },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = "${ComponentDetails.route}/{componentId}",
            arguments = listOf(
                navArgument("componentId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("componentId") ?: return@composable
            val component by remember(id) { componentsViewModel.findById(id) }
                .collectAsStateLifecycleAware(initial = null)

            SideEffect {
                component?.name?.let { onTitleChanged(it) }
            }

            component?.let { ComponentDetailsScreen(it) }
        }
    }
}
