package io.github.tuguzt.pcbuilder.presentation.view.root.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.pcbuilder.presentation.R

/**
 * Google authentication light button.
 */
@Composable
fun GoogleAuthButton(
    onClick: (AuthVariant.Google) -> Unit,
    modifier: Modifier = Modifier,
) {
    MaterialTheme(colorScheme = lightColorScheme()) {
        ElevatedButton(
            onClick = { onClick(AuthVariant.Google) },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
            ),
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

@Preview
@Composable
private fun GoogleAuthButtonPreview() {
    GoogleAuthButton(onClick = {})
}
