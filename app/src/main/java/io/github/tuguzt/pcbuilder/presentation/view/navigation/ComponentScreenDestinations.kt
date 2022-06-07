package io.github.tuguzt.pcbuilder.presentation.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.root.main.components.ComponentsScreen

/**
 * Describable navigation destinations
 * of [*Components*][ComponentsScreen] main application destination.
 */
sealed class ComponentScreenDestinations(override val route: String) : DescribableDestination {
    object ComponentList : ComponentScreenDestinations(route = "componentList") {
        override val description: String
            @Composable get() = stringResource(R.string.components)
    }

    object Favorites : ComponentScreenDestinations(route = "favorites") {
        override val description: String
            @Composable get() = stringResource(R.string.favorites)
    }

    object SearchComponent : ComponentScreenDestinations(route = "searchComponent") {
        override val description: String
            @Composable get() = stringResource(R.string.search_components)
    }

    object CompareComponents : ComponentScreenDestinations(route = "compareComponents") {
        override val description: String
            @Composable get() = stringResource(R.string.compare_components)
    }

    object ChooseComponent : ComponentScreenDestinations(route = "chooseComponent") {
        override val description: String
            @Composable get() = stringResource(R.string.choose_component)
    }

    object ComponentDetails : ComponentScreenDestinations(route = "component") {
        override val description: String
            @Composable get() = stringResource(R.string.component_details)
    }
}
