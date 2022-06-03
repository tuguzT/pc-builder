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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.data.repository.backend.BackendResponse
import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.navigation.RootNavigationDestinations.Auth
import io.github.tuguzt.pcbuilder.presentation.view.navigation.navigateMain
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account.AccountViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

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
                authViewModel.googleOAuth2(account).handle(context, snackbarHostState) {
                    accountViewModel.updateUser().handle(context, snackbarHostState) {
                        navController.navigateMain()
                    }
                }
            }
        }

    composable(Auth.SignIn.route) {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        val launcher = rememberGoogleAuthLauncher(context, coroutineScope, snackbarHostState)

        val onSignIn: (AuthVariant) -> Unit = { variant ->
            when (variant) {
                is AuthVariant.Credentials -> {
                    val credentials = variant.credentials.toData()
                    coroutineScope.launch {
                        authViewModel.auth(credentials).handle(context, snackbarHostState) {
                            accountViewModel.updateUser().handle(context, snackbarHostState) {
                                navController.navigateMain()
                            }
                        }
                    }
                }
                AuthVariant.Google -> {
                    val intent = authViewModel.googleSignInIntent
                    launcher.launch(intent)
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
        val context = LocalContext.current

        val launcher = rememberGoogleAuthLauncher(context, coroutineScope, snackbarHostState)

        val onSignUp: (AuthVariant) -> Unit = { variant ->
            when (variant) {
                is AuthVariant.Credentials -> {
                    val credentials = variant.credentials.toData()
                    coroutineScope.launch {
                        authViewModel.register(credentials).handle(context, snackbarHostState) {
                            accountViewModel.updateUser().handle(context, snackbarHostState) {
                                navController.navigateMain()
                            }
                        }
                    }
                }
                AuthVariant.Google -> {
                    val intent = authViewModel.googleSignInIntent
                    launcher.launch(intent)
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
 * Convert [UserCredentials] to [UserCredentialsData].
 */
private fun UserCredentials.toData() = UserCredentialsData(username, password)

/**
 * Handles backend auth errors and shows it to the user.
 */
private suspend inline fun <S> BackendResponse<S>.handle(
    context: Context,
    snackbarHostState: SnackbarHostState,
    onSuccess: (S) -> Unit,
) {
    when (this) {
        is NetworkResponse.Success -> onSuccess(body)
        is NetworkResponse.ServerError -> {
            logger.error(error) { "Server error occurred" }
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.server_error),
                actionLabel = context.getString(R.string.dismiss),
            )
        }
        is NetworkResponse.NetworkError -> {
            logger.error(error) { "Network error occurred" }
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.network_error),
                actionLabel = context.getString(R.string.dismiss),
            )
        }
        is NetworkResponse.UnknownError -> {
            logger.error(error) { "Unknown error occurred" }
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.unknown_error),
                actionLabel = context.getString(R.string.dismiss),
            )
        }
    }
}
