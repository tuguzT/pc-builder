package io.github.tuguzt.pcbuilder.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.view.auth.AuthVariant
import io.github.tuguzt.pcbuilder.view.auth.SignInScreen
import io.github.tuguzt.pcbuilder.view.auth.SignUpScreen
import io.github.tuguzt.pcbuilder.view.navigation.RootNavigationDestinations.*
import io.github.tuguzt.pcbuilder.viewmodel.account.AccountViewModel
import io.github.tuguzt.pcbuilder.viewmodel.account.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Root screen of the PC Builder application.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(
    accountViewModel: AccountViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    LaunchedEffect(Unit) {
        when (accountViewModel.updateUser()) {
            is NetworkResponse.Success -> when (accountViewModel.currentUser) {
                null -> navController.navigateAuth()
                else -> navController.navigateMain()
            }
            else -> navController.navigateAuth() // todo normal error handling
        }
    }

    Scaffold { padding ->
        NavHost(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            navController = navController,
            startDestination = Splash.route,
        ) {
            composable(Splash.route) {
                Box(modifier = Modifier.fillMaxSize()) {}
            }
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
}

/**
 * Configures *Authentication* user flow.
 */
private fun NavGraphBuilder.authGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
) = navigation(startDestination = Auth.SignIn.route, route = Auth.route) {
    composable(Auth.SignIn.route) {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        val dismissText = stringResource(R.string.dismiss)
        val onSignIn: (AuthVariant) -> Unit = { variant ->
            when (variant) {
                is AuthVariant.Credentials -> {
                    val credentials = UserCredentialsData(
                        variant.credentials.username,
                        variant.credentials.password,
                    )
                    coroutineScope.launch {
                        when (val response = authViewModel.auth(credentials)) {
                            is NetworkResponse.Success -> {
                                when (val innerResponse = accountViewModel.findUser()) {
                                    is NetworkResponse.Success -> withContext(Dispatchers.Main) {
                                        navController.navigateMain()
                                    }
                                    is NetworkResponse.Error -> {
                                        withContext(Dispatchers.Main) {
                                            logger.error { innerResponse }
                                        }
                                        snackbarHostState.showSnackbar(
                                            message = "Something wrong happened...",
                                            actionLabel = dismissText,
                                        )
                                    }
                                }
                            }
                            is NetworkResponse.Error -> {
                                withContext(Dispatchers.Main) {
                                    logger.error { response }
                                }
                                snackbarHostState.showSnackbar(
                                    message = "Something wrong happened...",
                                    actionLabel = dismissText,
                                )
                            }
                        }
                    }
                }
                AuthVariant.Google -> {
                    // todo Google auth
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Google auth is not supported for now",
                            actionLabel = dismissText,
                        )
                    }
                }
            }
        }

        SignInScreen(
            onSignIn = onSignIn,
            viewModel = authViewModel,
            onSignUpNavigate = {
                navController.navigate(Auth.SignUp.route) {
                    popUpTo(Auth.route) { inclusive = true }
                }
            },
            snackbarHostState = snackbarHostState,
        )
    }
    composable(Auth.SignUp.route) {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        val dismissText = stringResource(R.string.dismiss)
        val onSignUp: (AuthVariant) -> Unit = { variant ->
            when (variant) {
                is AuthVariant.Credentials -> {
                    val credentials = UserCredentialsData(
                        variant.credentials.username,
                        variant.credentials.password,
                    )
                    coroutineScope.launch {
                        when (val response = authViewModel.register(credentials)) {
                            is NetworkResponse.Success -> {
                                when (val innerResponse = accountViewModel.findUser()) {
                                    is NetworkResponse.Success -> withContext(Dispatchers.Main) {
                                        navController.navigateMain()
                                    }
                                    is NetworkResponse.Error -> {
                                        withContext(Dispatchers.Main) {
                                            logger.error { innerResponse }
                                        }
                                        snackbarHostState.showSnackbar(
                                            message = "Something wrong happened...",
                                            actionLabel = dismissText,
                                        )
                                    }
                                }
                            }
                            is NetworkResponse.Error -> {
                                withContext(Dispatchers.Main) {
                                    logger.error { response }
                                }
                                snackbarHostState.showSnackbar(
                                    message = "Something wrong happened...",
                                    actionLabel = dismissText,
                                )
                            }
                        }
                    }
                }
                AuthVariant.Google -> {
                    // todo Google auth
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Google auth is not supported for now",
                            actionLabel = dismissText,
                        )
                    }
                }
            }
        }

        SignUpScreen(
            onSignUp = onSignUp,
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
