package io.github.tuguzt.pcbuilder.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.BuildCircle
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.pcbuilder.R

sealed class MainScreenDestinations(
    val route: String,
    private val composableText: @Composable () -> String,
    val imageVector: ImageVector,
) {
    val text @Composable get() = composableText()

    object Components : MainScreenDestinations(
        route = "components",
        composableText = { stringResource(R.string.components) },
        imageVector = Icons.Rounded.Memory,
    )

    object Builds : MainScreenDestinations(
        route = "builds",
        composableText = { stringResource(R.string.builds) },
        imageVector = Icons.Rounded.BuildCircle,
    )

    object Account : MainScreenDestinations(
        route = "account",
        composableText = { stringResource(R.string.account) },
        imageVector = Icons.Rounded.AccountCircle,
    )
}
