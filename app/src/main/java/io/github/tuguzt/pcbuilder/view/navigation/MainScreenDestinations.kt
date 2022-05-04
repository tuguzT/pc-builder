package io.github.tuguzt.pcbuilder.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.view.MainScreen

/**
 * Bottom navigation destinations of the application's [main screen][MainScreen].
 */
sealed class MainScreenDestinations(
    override val route: String,
    override val composableDescription: @Composable () -> String,
    override val imageVector: ImageVector,
) : BottomNavigationDestination() {

    object Components : MainScreenDestinations(
        route = "components",
        composableDescription = { stringResource(R.string.components) },
        imageVector = Icons.Rounded.Memory,
    )

    object Builds : MainScreenDestinations(
        route = "builds",
        composableDescription = { stringResource(R.string.builds) },
        imageVector = Icons.Rounded.Computer,
    )

    object Account : MainScreenDestinations(
        route = "account",
        composableDescription = { stringResource(R.string.account) },
        imageVector = Icons.Rounded.AccountCircle,
    )
}
