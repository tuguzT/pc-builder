package io.github.tuguzt.pcbuilder.presentation.view.root.main

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.ToastDuration
import io.github.tuguzt.pcbuilder.presentation.view.navigation.ComponentScreenDestinations.*
import io.github.tuguzt.pcbuilder.presentation.view.navigation.MainScreenDestinations.*
import io.github.tuguzt.pcbuilder.presentation.view.root.main.account.AccountScreen
import io.github.tuguzt.pcbuilder.presentation.view.root.main.builds.BuildsScreen
import io.github.tuguzt.pcbuilder.presentation.view.root.main.components.ComponentsScreen
import io.github.tuguzt.pcbuilder.presentation.view.showToast
import io.github.tuguzt.pcbuilder.presentation.view.utils.DestinationsNavigationBar
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.*
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account.AccountViewModel

/**
 * Main screen of the application.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    accountViewModel: AccountViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val appName = stringResource(R.string.app_name)
    LaunchedEffect(Unit) {
        mainViewModel.updateTitle(appName)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    LaunchedEffect(currentRoute) {
        val currentDestination = when (currentRoute) {
            Components.route -> Components
            Builds.route -> Builds
            Account.route -> Account
            else -> return@LaunchedEffect
        }
        mainViewModel.updateCurrentDestination(currentDestination)
    }

    val componentsNavController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            MainScreenTopAppBar(
                viewModel = mainViewModel,
                componentsNavController = componentsNavController,
            )
        },
        bottomBar = {
            DestinationsNavigationBar(
                navController = navController,
                destinations = listOf(Components, Builds, Account),
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Components.route,
            modifier = Modifier.padding(padding),
        ) {
            composable(Components.route) {
                ComponentsScreen(
                    mainViewModel = mainViewModel,
                    navController = componentsNavController,
                    snackbarHostState = snackbarHostState,
                )
            }
            composable(Builds.route) {
                BuildsScreen(mainViewModel)
            }
            composable(Account.route) account@{
                val user = accountViewModel.uiState.currentUser ?: return@account

                val context = LocalContext.current
                val toastText = stringResource(R.string.signed_out_success)
                AccountScreen(
                    user = user,
                    mainViewModel = mainViewModel,
                    onSignOut = {
                        showToast(context, toastText, ToastDuration.Short)
                        accountViewModel.signOut()
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainScreenTopAppBar(
    viewModel: MainViewModel,
    componentsNavController: NavHostController,
) {
    val tonalElevation by animateDpAsState(if (viewModel.uiState.isFilled) 4.dp else 0.dp)

    Surface(tonalElevation = tonalElevation) {
        CenterAlignedTopAppBar(
            title = {
                AnimatedContent(targetState = viewModel.uiState.title) { title -> Text(title) }
            },
            navigationIcon = {
                AnimatedVisibility(
                    visible = viewModel.uiState.navigationVisible,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally(),
                ) {
                    IconButton(onClick = viewModel.uiState.onNavigateUpAction) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_up),
                        )
                    }
                }
            },
            actions = {
                AnimatedVisibility(visible = viewModel.uiState.menuVisible) {
                    IconButton(onClick = { viewModel.updateMenuExpanded(expanded = true) }) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = stringResource(R.string.show_more),
                        )
                    }
                }
                DropdownMenu(
                    expanded = viewModel.uiState.menuExpanded,
                    onDismissRequest = { viewModel.updateMenuExpanded(expanded = false) },
                ) {
                    if (viewModel.uiState.searchVisible) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.search_components)) },
                            onClick = { componentsNavController.navigate(SearchComponent.route) },
                        )
                    }
                    if (viewModel.uiState.favoritesVisible) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.favorite_components)) },
                            onClick = { componentsNavController.navigate(Favorites.route) },
                        )
                    }
                    if (viewModel.uiState.compareVisible) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.compare_components)) },
                            onClick = { componentsNavController.navigate(CompareComponents.route) },
                        )
                    }
                }
            },
        )
    }
}
