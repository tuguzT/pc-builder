package io.github.tuguzt.pcbuilder.view.auth

import android.content.res.Configuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme

/**
 * Application user sign in screen.
 */
@Composable
fun SignInScreen(
    onSignIn: (AuthVariant) -> Unit,
    onSignUpNavigate: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    AuthScreen(snackbarHostState) {
        AuthScreenContent(
            title = stringResource(R.string.sign_in),
            username = username,
            onUsernameChange = { username = it },
            password = password,
            onPasswordChange = { password = it },
            passwordVisible = passwordVisible,
            onPasswordVisibleChange = { passwordVisible = !passwordVisible },
            onAuth = onSignIn,
            alternativeDestinationDescription = stringResource(R.string.no_account),
            alternativeDestinationText = stringResource(R.string.sign_up),
            onAlternativeNavigate = onSignUpNavigate,
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SignInScreenPreview() {
    PCBuilderTheme {
        SignInScreen(onSignIn = {}, onSignUpNavigate = {})
    }
}
