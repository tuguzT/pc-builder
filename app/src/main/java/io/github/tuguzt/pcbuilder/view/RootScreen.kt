package io.github.tuguzt.pcbuilder.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.github.tuguzt.pcbuilder.view.auth.AuthVariant
import io.github.tuguzt.pcbuilder.view.auth.SignInScreen
import io.github.tuguzt.pcbuilder.view.auth.SignUpScreen
import io.github.tuguzt.pcbuilder.view.navigation.RootNavigationDestinations.*
import io.github.tuguzt.pcbuilder.viewmodel.AccountViewModel
import io.github.tuguzt.pcbuilder.viewmodel.AuthViewModel

/**
 * Root screen of the PC Builder application.
 */
@Composable
fun RootScreen(navController: NavHostController = rememberNavController()) {
    val accountViewModel = viewModel<AccountViewModel>()
    val authViewModel = viewModel<AuthViewModel>()

    NavHost(navController = navController, startDestination = Main.route) {
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
    imageUri = "https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg",
)

/**
 * Configures *Authentication* user flow.
 */
private fun NavGraphBuilder.authGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
) = navigation(startDestination = Auth.SignIn.route, route = Auth.route) {
    val userFromCredentials = { credentials: UserCredentials ->
        UserData(
            id = randomNanoId(),
            role = UserRole.User,
            username = credentials.username,
            email = null,
            imageUri = null,
        )
    }
    composable(Auth.SignIn.route) {
        SignInScreen(
            onSignIn = {
                val newUser = when (it) {
                    is AuthVariant.Credentials -> userFromCredentials(it.credentials)
                    AuthVariant.Google -> googleUser
                }
                accountViewModel.currentUser = newUser
                navController.navigateMain()
            },
            viewModel = authViewModel,
            onSignUpNavigate = { navController.navigate(Auth.SignUp.route) },
        )
    }
    composable(Auth.SignUp.route) {
        SignUpScreen(
            onSignUp = {
                val newUser = when (it) {
                    is AuthVariant.Credentials -> userFromCredentials(it.credentials)
                    AuthVariant.Google -> googleUser
                }
                accountViewModel.currentUser = newUser
                navController.navigateMain()
            },
            viewModel = authViewModel,
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
