package io.github.tuguzt.pcbuilder.view.auth

import android.content.res.Configuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme
import io.github.tuguzt.pcbuilder.viewmodel.AuthViewModel

/**
 * Application user sign up screen.
 */
@Composable
fun SignUpScreen(
    onSignUp: (AuthVariant) -> Unit,
    onSignInNavigate: () -> Unit,
    viewModel: AuthViewModel = viewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) = AuthScreen(snackbarHostState) {
    AuthScreenContent(
        title = stringResource(R.string.sign_up),
        viewModel = viewModel,
        onAuth = onSignUp,
        alternativeDestinationDescription = stringResource(R.string.have_account),
        alternativeDestinationText = stringResource(R.string.sign_in),
        onAlternativeNavigate = onSignInNavigate,
    )
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
