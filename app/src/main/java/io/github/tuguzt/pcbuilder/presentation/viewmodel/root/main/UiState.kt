package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main

import io.github.tuguzt.pcbuilder.presentation.view.navigation.Destination
import io.github.tuguzt.pcbuilder.presentation.view.navigation.MainScreenDestinations

data class MainState(
    val title: String = "",
    val isFilled: Boolean = false,
    val currentDestination: Destination = MainScreenDestinations.Components,
    val onNavigateUpAction: () -> Unit = {},
)

inline val MainState.favoritesVisible: Boolean
    get() = currentDestination == MainScreenDestinations.Components

inline val MainState.searchVisible: Boolean
    get() = favoritesVisible

inline val MainState.navigationVisible: Boolean
    get() = currentDestination !is MainScreenDestinations
