package io.github.tuguzt.pcbuilder.view.navigation

import androidx.compose.material.BottomNavigationItem
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents **bottom navigation** destinations for Compose Navigation.
 *
 * Provides [icon] for [bottom navigation item][BottomNavigationItem].
 */
sealed interface BottomNavigationDestination : DescribableDestination {
    val icon: ImageVector
}
