package io.github.tuguzt.pcbuilder.presentation.view.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.tuguzt.pcbuilder.presentation.view.navigation.RootNavigationDestinations
import io.github.tuguzt.pcbuilder.presentation.view.navigation.RootNavigationDestinations.Main
import io.github.tuguzt.pcbuilder.presentation.view.navigation.navigateAuth
import io.github.tuguzt.pcbuilder.presentation.view.root.auth.authGraph
import io.github.tuguzt.pcbuilder.presentation.view.root.main.MainScreen
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth.AuthViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account.AccountViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account.signedIn

/**
 * Root screen of the PC Builder application.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(
    startDestination: RootNavigationDestinations,
    accountViewModel: AccountViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    Scaffold { padding ->
        NavHost(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            navController = navController,
            startDestination = startDestination.route,
        ) {
            composable(Main.route) {
                LaunchedEffect(accountViewModel.uiState) {
                    if (accountViewModel.uiState.isLoading || authViewModel.uiState.isLoading)
                        return@LaunchedEffect

                    if (!accountViewModel.uiState.signedIn) {
                        authViewModel.updateIsLoggedIn(isLoggedIn = false)
                        navController.navigateAuth()
                    }
                }
                MainScreen(accountViewModel)
            }
            authGraph(navController, authViewModel, accountViewModel)
        }
    }
}
