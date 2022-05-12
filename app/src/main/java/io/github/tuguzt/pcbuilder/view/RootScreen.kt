package io.github.tuguzt.pcbuilder.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.github.tuguzt.pcbuilder.view.auth.AuthVariant
import io.github.tuguzt.pcbuilder.view.auth.SignInScreen
import io.github.tuguzt.pcbuilder.view.auth.SignUpScreen
import io.github.tuguzt.pcbuilder.view.navigation.RootNavigationDestinations.Auth
import io.github.tuguzt.pcbuilder.view.navigation.RootNavigationDestinations.Main
import io.github.tuguzt.pcbuilder.viewmodel.AccountViewModel
import io.github.tuguzt.pcbuilder.viewmodel.AuthViewModel

/**
 * Root screen of the PC Builder application.
 */
@Composable
fun RootScreen(
    accountViewModel: AccountViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val startDestination = when (accountViewModel.currentUser) {
        null -> Auth
        else -> Main
    }
    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(Main.route) {
            val onSignOut = {
                navController.navigateAuth()
                accountViewModel.currentUser = null
            }
            MainScreen(onSignOut = onSignOut, accountViewModel = accountViewModel)
        }
        authGraph(
            navController = navController,
            accountViewModel = accountViewModel,
            authViewModel = authViewModel,
        )
    }
}

// TODO remove in the future
private val googleUser = UserData(
    id = randomNanoId(),
    role = UserRole.User,
    username = "UnknownGoogleUser",
    email = null,
    imageUri = "https://www.freepnglogos.com/uploads/google-logo-png/google-logo-png-webinar-optimizing-for-success-google-business-webinar-13.png",
)

// TODO remove in the future
private fun userFromCredentials(credentials: UserCredentials): User = UserData(
    id = randomNanoId(),
    role = UserRole.User,
    username = credentials.username,
    email = null,
    imageUri = null,
)

/**
 * Configures *Authentication* user flow.
 */
private fun NavGraphBuilder.authGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
) = navigation(startDestination = Auth.SignIn.route, route = Auth.route) {
    composable(Auth.SignIn.route) {
        SignInScreen(
            onSignIn = { variant ->
                val newUser = when (variant) {
                    is AuthVariant.Credentials -> userFromCredentials(variant.credentials)
                    AuthVariant.Google -> googleUser
                }
                accountViewModel.currentUser = newUser
                navController.navigateMain()
            },
            viewModel = authViewModel,
            onSignUpNavigate = {
                navController.navigate(Auth.SignUp.route) {
                    popUpTo(Auth.route) { inclusive = true }
                }
            },
        )
    }
    composable(Auth.SignUp.route) {
        SignUpScreen(
            onSignUp = { variant ->
                val newUser = when (variant) {
                    is AuthVariant.Credentials -> userFromCredentials(variant.credentials)
                    AuthVariant.Google -> googleUser
                }
                accountViewModel.currentUser = newUser
                navController.navigateMain()
            },
            viewModel = authViewModel,
            onSignInNavigate = {
                navController.navigate(Auth.SignIn.route) {
                    popUpTo(Auth.route) { inclusive = true }
                }
            },
        )
    }
}

/**
 * Navigate to the *Authentication* user flow.
 */
private fun NavController.navigateAuth() = navigate(Auth.route) {
    popUpTo(Main.route) {
        inclusive = true
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

/**
 * Navigate to the [main screen][MainScreen].
 */
private fun NavController.navigateMain() = navigate(Main.route) {
    popUpTo(Auth.route) {
        inclusive = true
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
