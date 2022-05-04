package io.github.tuguzt.pcbuilder.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents **bottom navigation** destinations for Compose Navigation.
 */
sealed class BottomNavigationDestination : Destination {
    protected abstract val composableDescription: @Composable () -> String
    val description: String @Composable get() = composableDescription()

    abstract val imageVector: ImageVector
}
