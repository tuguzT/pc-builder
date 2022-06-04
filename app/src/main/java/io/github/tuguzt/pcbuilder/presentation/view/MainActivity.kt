package io.github.tuguzt.pcbuilder.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import io.github.tuguzt.pcbuilder.presentation.view.navigation.RootNavigationDestinations
import io.github.tuguzt.pcbuilder.presentation.view.root.RootScreen
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account.AccountViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account.signedIn

/**
 * Entry point of the application.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            accountViewModel.uiState.isLoading
        }

        super.onCreate(savedInstanceState)
        setContent {
            PCBuilderTheme {
                val startDestination = when (accountViewModel.uiState.signedIn) {
                    true -> RootNavigationDestinations.Main
                    else -> RootNavigationDestinations.Auth
                }
                RootScreen(
                    startDestination = startDestination,
                    accountViewModel = accountViewModel,
                )
            }
        }
    }
}
