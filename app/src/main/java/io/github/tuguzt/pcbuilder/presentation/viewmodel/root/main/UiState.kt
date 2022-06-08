package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main

import io.github.tuguzt.pcbuilder.presentation.view.navigation.BuildScreenDestinations
import io.github.tuguzt.pcbuilder.presentation.view.navigation.Destination
import io.github.tuguzt.pcbuilder.presentation.view.navigation.MainScreenDestinations

data class MainState(
    val title: String = "",
    val isFilled: Boolean = false,
    val menuExpanded: Boolean = false,
    val currentDestination: Destination = MainScreenDestinations.Components,
    val onNavigateUpAction: () -> Unit = {},
    val onBuildEditAction: () -> Unit = {},
    val onBuildCompatibilityAction: () -> Unit = {},
)

inline val MainState.menuVisible: Boolean
    get() = favoritesVisible || buildCompatibilityVisible

inline val MainState.favoritesVisible: Boolean
    get() = currentDestination == MainScreenDestinations.Components

inline val MainState.searchVisible: Boolean
    get() = favoritesVisible

inline val MainState.compareVisible: Boolean
    get() = favoritesVisible

inline val MainState.navigationVisible: Boolean
    get() = currentDestination !is MainScreenDestinations

inline val MainState.buildCompatibilityVisible: Boolean
    get() = currentDestination == BuildScreenDestinations.BuildDetails

inline val MainState.buildEditVisible: Boolean
    get() = buildCompatibilityVisible

inline val MainState.addBuildVisible: Boolean
    get() = currentDestination == MainScreenDestinations.Builds
