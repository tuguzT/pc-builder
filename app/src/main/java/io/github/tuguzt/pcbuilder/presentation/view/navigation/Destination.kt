package io.github.tuguzt.pcbuilder.presentation.view.navigation

/**
 * Represents destinations for Compose Navigation.
 */
sealed interface Destination {
    val route: String
}
