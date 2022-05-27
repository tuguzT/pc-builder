package io.github.tuguzt.pcbuilder.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
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
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme
import io.github.tuguzt.pcbuilder.viewmodel.ComponentListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Application screen which represents *Components* main application destination.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ComponentsScreen(
    onTitleChanged: (String) -> Unit,
    componentListViewModel: ComponentListViewModel = viewModel(),
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
) {
    val appName = stringResource(R.string.app_name)

    val components by componentListViewModel.components
        .collectAsStateLifecycleAware(scope.coroutineContext)
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(navController = navController, startDestination = ComponentList.route) {
        composable(ComponentList.route) {
            LaunchedEffect(true) { onTitleChanged(appName) }

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
                    componentListViewModel += component
                    navController.popBackStack()
                    scope.launch {
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
            val id = backStackEntry.arguments?.getString("componentId")
            val component = checkNotNull(components.find { it.id == id })
            LaunchedEffect(Unit) { onTitleChanged(component.name) }

            ComponentDetailsScreen(component)
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ComponentsScreenPreview() {
    PCBuilderTheme {
        ComponentsScreen(onTitleChanged = {})
    }
}
