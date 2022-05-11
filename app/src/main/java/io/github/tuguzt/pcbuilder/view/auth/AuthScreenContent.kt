package io.github.tuguzt.pcbuilder.view.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.view.utils.PasswordTextField
import io.github.tuguzt.pcbuilder.viewmodel.AuthViewModel

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
    viewModel: AuthViewModel = viewModel(),
) {
    Text(text = title, style = MaterialTheme.typography.h4)
    Spacer(modifier = Modifier.height(32.dp))

    OutlinedTextField(
        value = viewModel.username,
        onValueChange = { viewModel.username = it },
        label = { Text(stringResource(R.string.username)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
    )
    Spacer(modifier = Modifier.height(16.dp))

    PasswordTextField(
        password = viewModel.password,
        onPasswordChange = { viewModel.password = it },
        modifier = Modifier.fillMaxWidth(0.8f),
        passwordVisible = viewModel.passwordVisible,
        onPasswordVisibleChange = { viewModel.passwordVisible = it },
    )
    Spacer(modifier = Modifier.height(32.dp))

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colors.onBackground)) {
            append(alternativeDestinationDescription)
            append(' ')
        }

        withAnnotation(tag = "alternative", annotation = "Go to alternative") {
            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                append(alternativeDestinationText)
            }
        }
    }
    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.button,
    ) { offset ->
        annotatedString
            .getStringAnnotations(tag = "alternative", start = offset, end = offset)
            .firstOrNull()
            ?.let { onAlternativeNavigate() }
    }
    Spacer(modifier = Modifier.height(8.dp))
    CredentialsAuthButton(
        username = viewModel.username,
        password = viewModel.password,
        onClick = { onAuth(it) },
        content = { Text(title) },
    )

    Spacer(modifier = Modifier.height(48.dp))
    Divider(
        modifier = Modifier.fillMaxWidth(0.45f),
        color = MaterialTheme.colors.primary,
        thickness = 2.dp,
    )
    Spacer(modifier = Modifier.height(48.dp))

    GoogleAuthButton(onClick = { onAuth(AuthVariant.Google) })
}
