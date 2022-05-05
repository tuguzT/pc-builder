package io.github.tuguzt.pcbuilder.view.auth

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.model.user.UserCredentialsData
import io.github.tuguzt.pcbuilder.view.theme.PCBuilderTheme

/**
 * Application user login screen.
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun LoginScreen(
    onAuth: (AuthVariant) -> Unit,
    onRegisterNavigate: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
    val focusManager = LocalFocusManager.current

    Scaffold(scaffoldState = scaffoldState) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = focusManager::clearFocus,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.h4,
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
                visualTransformation = when {
                    passwordVisible -> VisualTransformation.None
                    else -> PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = when {
                        passwordVisible -> Icons.Filled.Visibility
                        else -> Icons.Filled.VisibilityOff
                    }
                    val contentDescription = when {
                        passwordVisible -> stringResource(R.string.hide_password)
                        else -> stringResource(R.string.show_password)
                    }
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = contentDescription)
                    }
                },
            )
            Spacer(modifier = Modifier.height(32.dp))

            val noAccountText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colors.onBackground)) {
                    append(stringResource(R.string.no_account))
                    append(' ')
                }

                withAnnotation(tag = "register", annotation = "Some text AAAAAAAAAA") {
                    withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                        append(stringResource(R.string.register))
                    }
                }
            }
            ClickableText(
                text = noAccountText,
                style = MaterialTheme.typography.button,
            ) { offset ->
                noAccountText
                    .getStringAnnotations(tag = "register", start = offset, end = offset)
                    .firstOrNull()
                    ?.let { onRegisterNavigate() }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val credentials = UserCredentialsData(username, password)
                    onAuth(AuthVariant.Credentials(credentials))
                },
                enabled = checkUsername(username) && checkPassword(password),
            ) {
                Text(stringResource(R.string.sign_in))
            }

            Spacer(modifier = Modifier.height(48.dp))
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth(0.45f)
                    .background(MaterialTheme.colors.primary),
            )
            Spacer(modifier = Modifier.height(48.dp))

            MaterialTheme(colors = lightColors()) {
                Button(
                    onClick = { onAuth(AuthVariant.Google) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = stringResource(R.string.sign_in_google),
                    )
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(R.string.sign_in_google))
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun LoginScreenPreview() {
    PCBuilderTheme {
        LoginScreen(onAuth = {}, onRegisterNavigate = {})
    }
}
