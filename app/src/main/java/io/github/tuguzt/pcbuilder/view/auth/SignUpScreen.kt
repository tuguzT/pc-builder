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
 * Application user sign up screen.
 */
@Composable
fun SignUpScreen(
    onSignUp: (AuthVariant) -> Unit,
    onSignInNavigate: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    AuthScreen(snackbarHostState) {
        AuthScreenContent(
            title = stringResource(R.string.sign_up),
            username = username,
            onUsernameChange = { username = it },
            password = password,
            onPasswordChange = { password = it },
            passwordVisible = passwordVisible,
            onPasswordVisibleChange = { passwordVisible = !passwordVisible },
            onAuth = onSignUp,
            alternativeDestinationDescription = stringResource(R.string.have_account),
            alternativeDestinationText = stringResource(R.string.sign_in),
            onAlternativeNavigate = onSignInNavigate,
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SignUpScreenPreview() {
    PCBuilderTheme {
        SignUpScreen(onSignUp = {}, onSignInNavigate = {})
    }
}
