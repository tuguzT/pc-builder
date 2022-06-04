package io.github.tuguzt.pcbuilder.presentation.view.root.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.utils.PasswordTextField
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth.AuthViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth.isValid

/**
 * Base screen content for all authentication screens.
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun AuthScreenContent(
    title: String,
    onAuth: (AuthVariant) -> Unit,
    alternativeDestinationDescription: String,
    alternativeDestinationText: String,
    onAlternativeNavigate: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.headlineLarge)
    Spacer(modifier = Modifier.height(32.dp))

    OutlinedTextField(
        value = viewModel.uiState.username,
        onValueChange = { viewModel.updateUsername(it) },
        label = { Text(stringResource(R.string.username)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
    )
    Spacer(modifier = Modifier.height(16.dp))

    PasswordTextField(
        password = viewModel.uiState.password,
        onPasswordChange = { viewModel.updatePassword(it) },
        modifier = Modifier.fillMaxWidth(0.8f),
        passwordVisible = viewModel.uiState.passwordVisible,
        onPasswordVisibleChange = { viewModel.updatePasswordVisible(it) },
    )
    Spacer(modifier = Modifier.height(32.dp))

    Button(
        onClick = { onAuth(AuthVariant.Credentials) },
        enabled = viewModel.uiState.isValid,
        content = { Text(title) },
    )
    Spacer(modifier = Modifier.height(24.dp))

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(alternativeDestinationDescription)
            append(' ')
        }

        // move here to avoid strange compilation error from Compose
        val style = SpanStyle(color = MaterialTheme.colorScheme.primary)
        withAnnotation(tag = "alternative", annotation = alternativeDestinationText) {
            withStyle(style = style) {
                append(alternativeDestinationText)
            }
        }
    }
    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.labelLarge,
    ) { offset ->
        annotatedString
            .getStringAnnotations(tag = "alternative", start = offset, end = offset)
            .firstOrNull()
            ?.let { onAlternativeNavigate() }
    }

    Spacer(modifier = Modifier.height(32.dp))
    Divider(
        modifier = Modifier.fillMaxWidth(0.45f),
        color = MaterialTheme.colorScheme.primary,
        thickness = 2.dp,
    )
    Spacer(modifier = Modifier.height(32.dp))

    GoogleAuthButton(onClick = { onAuth(AuthVariant.Google) })
}
