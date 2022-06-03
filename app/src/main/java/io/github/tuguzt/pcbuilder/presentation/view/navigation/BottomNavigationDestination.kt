package io.github.tuguzt.pcbuilder.presentation.view.navigation

import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents **bottom navigation** destinations for Compose Navigation.
 *
 * Provides [icon] for [bottom navigation item][NavigationBarItem].
 */
sealed interface BottomNavigationDestination : DescribableDestination {
    val icon: ImageVector
}
