package io.github.tuguzt.pcbuilder.presentation.view.root.auth

import android.content.res.Configuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth.AuthViewModel

/**
 * Application user sign in screen.
 */
@Composable
fun SignInScreen(
    onSignIn: (AuthVariant) -> Unit,
    onSignUpNavigate: () -> Unit,
    viewModel: AuthViewModel = viewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) = AuthScreen(snackbarHostState) {
    AuthScreenContent(
        title = stringResource(R.string.sign_in),
        viewModel = viewModel,
        onAuth = onSignIn,
        alternativeDestinationDescription = stringResource(R.string.no_account),
        alternativeDestinationText = stringResource(R.string.sign_up),
        onAlternativeNavigate = onSignUpNavigate,
    )
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
