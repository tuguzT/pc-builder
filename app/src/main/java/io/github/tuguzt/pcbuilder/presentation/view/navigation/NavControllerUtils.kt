package io.github.tuguzt.pcbuilder.presentation.view.navigation

import androidx.navigation.NavController
import io.github.tuguzt.pcbuilder.presentation.view.MainScreen
import io.github.tuguzt.pcbuilder.presentation.view.navigation.RootNavigationDestinations.Auth
import io.github.tuguzt.pcbuilder.presentation.view.navigation.RootNavigationDestinations.Main

/**
 * Navigate to the *Authentication* user flow.
 */
fun NavController.navigateAuth(popUpToDestination: Destination = Main) = navigate(Auth.route) {
    popUpTo(popUpToDestination.route) {
        inclusive = true
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

/**
 * Navigate to the [main screen][MainScreen].
 */
fun NavController.navigateMain(popUpToDestination: Destination = Auth) = navigate(Main.route) {
    popUpTo(popUpToDestination.route) {
        inclusive = true
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
