package io.github.tuguzt.pcbuilder.presentation.view.root.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.tuguzt.pcbuilder.presentation.view.navigation.RootNavigationDestinations.Splash
import io.github.tuguzt.pcbuilder.presentation.view.navigation.navigateAuth
import io.github.tuguzt.pcbuilder.presentation.view.navigation.navigateMain
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account.AccountViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account.signedIn

/**
 * Splash screen of the application.
 */
@Composable
fun SplashScreen(
    accountViewModel: AccountViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        accountViewModel.updateUser(context)
    }
    LaunchedEffect(accountViewModel.uiState) {
        if (accountViewModel.uiState.isUpdating) return@LaunchedEffect

        when (accountViewModel.uiState.signedIn) {
            true -> navController.navigateMain(Splash)
            else -> navController.navigateAuth(Splash)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Hello, World")
    }
}
