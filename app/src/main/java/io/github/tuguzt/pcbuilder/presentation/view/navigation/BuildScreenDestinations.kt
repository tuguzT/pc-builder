package io.github.tuguzt.pcbuilder.presentation.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.pcbuilder.presentation.R
import io.github.tuguzt.pcbuilder.presentation.view.root.main.builds.BuildsScreen

/**
 * Describable navigation destinations
 * of [*Builds*][BuildsScreen] main application destination.
 */
sealed class BuildScreenDestinations(override val route: String) : DescribableDestination {
    object BuildList : BuildScreenDestinations(route = "buildList") {
        override val description: String
            @Composable get() = stringResource(R.string.builds)
    }

    object AddBuild : BuildScreenDestinations(route = "addBuild") {
        override val description: String
            @Composable get() = stringResource(R.string.add_build)
    }

    object EditBuild : BuildScreenDestinations(route = "editBuild") {
        override val description: String
            @Composable get() = stringResource(R.string.build_edit)
    }

    object CompatibilityBuild : BuildScreenDestinations(route = "buildCompatibility") {
        override val description: String
            @Composable get() = stringResource(R.string.build_compatibility)
    }

    object BuildDetails : BuildScreenDestinations(route = "buildDetails") {
        override val description: String
            @Composable get() = stringResource(R.string.build_details)
    }
}
