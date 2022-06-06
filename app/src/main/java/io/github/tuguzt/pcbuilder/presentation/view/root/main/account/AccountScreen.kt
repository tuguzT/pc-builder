package io.github.tuguzt.pcbuilder.presentation.view.root.main.account

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.theme.PCBuilderTheme
import io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.MainViewModel

/**
 * Application screen which represents *Account* main application destination.
 */
@Composable
fun AccountScreen(
    user: User,
    mainViewModel: MainViewModel = hiltViewModel(),
    onSignOut: () -> Unit,
) {
    val appName = stringResource(R.string.app_name)
    SideEffect {
        mainViewModel.updateTitle(appName)
        mainViewModel.updateFilled(isFilled = false)
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        var imageState: AsyncImagePainter.State by remember {
            mutableStateOf(AsyncImagePainter.State.Empty)
        }
        if (imageState !is AsyncImagePainter.State.Error) {
            AsyncImage(
                model = user.imageUri,
                contentDescription = user.imageUri?.let { stringResource(R.string.user_avatar) },
                modifier = Modifier
                    .size(144.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .placeholder(
                        visible = imageState is AsyncImagePainter.State.Loading,
                        highlight = PlaceholderHighlight.fade(),
                    ),
                onState = { imageState = it },
            )
        } else {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = stringResource(R.string.avatar_not_loaded),
                modifier = Modifier
                    .size(144.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = user.username, style = MaterialTheme.typography.headlineSmall)
        if (user.role != UserRole.User) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${user.role}", style = MaterialTheme.typography.titleSmall)
        }
        val email = user.email
        if (email != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = email, style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onSignOut,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
        ) {
            Text(stringResource(R.string.sign_out))
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun AccountScreenPreview() {
    PCBuilderTheme {
        Surface {
            val user = UserData(
                id = randomNanoId(),
                role = UserRole.Administrator,
                username = "tuguzT",
                email = "timurka.tugushev@gmail.com",
                imageUri = "https://avatars.githubusercontent.com/u/56771526",
            )
            AccountScreen(user = user, onSignOut = {})
        }
    }
}
