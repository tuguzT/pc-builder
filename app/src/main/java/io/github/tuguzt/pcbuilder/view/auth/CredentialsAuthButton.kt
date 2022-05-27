package io.github.tuguzt.pcbuilder.view.auth

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData

/**
 * Material Design contained button which is enabled
 * only when [username] and [password] are correct.
 */
@Composable
fun CredentialsAuthButton(
    username: String,
    password: String,
    modifier: Modifier = Modifier,
    onClick: (AuthVariant.Credentials) -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = {
            val credentials = UserCredentialsData(username, password)
            onClick(AuthVariant.Credentials(credentials))
        },
        modifier = modifier,
        enabled = checkUsername(username) && checkPassword(password),
        content = content,
    )
}
