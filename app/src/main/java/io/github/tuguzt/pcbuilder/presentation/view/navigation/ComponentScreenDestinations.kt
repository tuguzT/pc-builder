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

    object AddComponent : ComponentScreenDestinations(route = "addComponent") {
        override val description: String
            @Composable get() = stringResource(R.string.add_new_component)
    }

    object RemoteSearchComponent : ComponentScreenDestinations(route = "remoteSearchComponent") {
        override val description: String
            @Composable get() = stringResource(R.string.search_components)
    }

    object ComponentDetails : ComponentScreenDestinations(route = "component") {
        override val description: String
            @Composable get() = stringResource(R.string.component_details)
    }
}
