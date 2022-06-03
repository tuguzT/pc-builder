package io.github.tuguzt.pcbuilder.presentation.view.navigation

import androidx.compose.runtime.Composable

/**
 * Represents destinations for Compose Navigation that can be described.
 */
sealed interface DescribableDestination : Destination {
    @get:Composable
    val description: String
}
