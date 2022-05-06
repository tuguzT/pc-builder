package io.github.tuguzt.pcbuilder.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import io.github.tuguzt.pcbuilder.view.auth.SignInScreen
import io.github.tuguzt.pcbuilder.view.auth.SignUpScreen
import io.github.tuguzt.pcbuilder.view.navigation.RootNavigationDestinations.*

/**
 * Root screen of the PC Builder application.
 */
@Composable
fun RootScreen(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Main.route) {
        composable(Main.route) {
            val onSignOut = {
                // todo clear user data
                navController.navigateAuth()
            }
            MainScreen(onSignOut = onSignOut)
        }
        authGraph(navController = navController)
    }
}

/**
 * Configures *Authentication* user flow.
 */
private fun NavGraphBuilder.authGraph(navController: NavController) =
    navigation(startDestination = Auth.SignIn.route, route = Auth.route) {
        composable(Auth.SignIn.route) {
            SignInScreen(
                onSignIn = {
                    // todo sign in existing user
                    navController.navigateMain()
                },
                onSignUpNavigate = { navController.navigate(Auth.SignUp.route) },
            )
        }
        composable(Auth.SignUp.route) {
            SignUpScreen(
                onSignUp = {
                    // todo sign up new user
                    navController.navigateMain()
                },
                onSignInNavigate = { navController.navigate(Auth.SignIn.route) },
            )
        }
    }

/**
 * Navigate to the *Authentication* user flow.
 */
private fun NavController.navigateAuth() = navigate(Auth.route) {
    popUpTo(Main.route) {
        inclusive = true
    }
}

/**
 * Navigate to the [main screen][MainScreen].
 */
private fun NavController.navigateMain() = navigate(Main.route) {
    popUpTo(Auth.route) {
        inclusive = true
    }
}
