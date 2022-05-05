package io.github.tuguzt.pcbuilder.view.auth

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)

    Scaffold(scaffoldState = scaffoldState) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.h4,
            )
            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username)) },
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

            Button(
                onClick = { onAuth(AuthVariant.Google) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_google_rounded),
                    contentDescription = stringResource(R.string.sign_in_google),
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    text = stringResource(R.string.sign_in_google),
                    color = Color.Black,
                )
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
