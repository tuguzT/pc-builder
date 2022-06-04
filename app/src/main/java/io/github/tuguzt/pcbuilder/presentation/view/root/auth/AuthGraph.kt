package io.github.tuguzt.pcbuilder.presentation.view.root.auth

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.navigation.RootNavigationDestinations.Auth
import io.github.tuguzt.pcbuilder.presentation.view.navigation.navigateMain
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth.AuthViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account.AccountViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Configures *Authentication* user flow.
 */
fun NavGraphBuilder.authGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
) = navigation(startDestination = Auth.SignIn.route, route = Auth.route) {

    @Composable
    fun rememberGoogleAuthLauncher(
        context: Context,
        coroutineScope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
    ): ManagedActivityResultLauncher<Intent, ActivityResult> =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode != RESULT_OK) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.google_auth_error),
                        actionLabel = context.getString(R.string.dismiss),
                    )
                }
                return@rememberLauncherForActivityResult
            }
            coroutineScope.launch {
                val account = GoogleSignIn.getSignedInAccountFromIntent(it.data).await()
                authViewModel.googleOAuth2(account, context)
            }
        }

    composable(Auth.SignIn.route) {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        val launcher = rememberGoogleAuthLauncher(context, coroutineScope, snackbarHostState)

        val onSignIn: (AuthVariant) -> Unit = { variant ->
            when (variant) {
                AuthVariant.Credentials -> authViewModel.auth(context)
                AuthVariant.Google -> {
                    val intent = authViewModel.googleSignInIntent
                    launcher.launch(intent)
                }
            }
        }

        LaunchedEffect(authViewModel.uiState) {
            if (authViewModel.uiState.isLoading) return@LaunchedEffect

            if (authViewModel.uiState.isLoggedIn) {
                accountViewModel.updateUser(context)
                navController.navigateMain()
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

        authViewModel.uiState.userMessages.firstOrNull()?.let { message ->
            LaunchedEffect(message) {
                snackbarHostState.showSnackbar(
                    message = message.message,
                    actionLabel = context.getString(R.string.dismiss),
                )
                authViewModel.userMessageShown(message.id)
            }
        }
    }
    composable(Auth.SignUp.route) {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        val launcher = rememberGoogleAuthLauncher(context, coroutineScope, snackbarHostState)

        val onSignUp: (AuthVariant) -> Unit = { variant ->
            when (variant) {
                AuthVariant.Credentials -> authViewModel.register(context)
                AuthVariant.Google -> {
                    val intent = authViewModel.googleSignInIntent
                    launcher.launch(intent)
                }
            }
        }

        LaunchedEffect(authViewModel.uiState) {
            if (authViewModel.uiState.isLoading) return@LaunchedEffect

            if (authViewModel.uiState.isLoggedIn) {
                accountViewModel.updateUser(context)
                navController.navigateMain()
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

        authViewModel.uiState.userMessages.firstOrNull()?.let { message ->
            LaunchedEffect(message) {
                snackbarHostState.showSnackbar(
                    message = message.message,
                    actionLabel = context.getString(R.string.dismiss),
                )
                authViewModel.userMessageShown(message.id)
            }
        }
    }
}
