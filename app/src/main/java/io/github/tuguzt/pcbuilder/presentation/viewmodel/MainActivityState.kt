package io.github.tuguzt.pcbuilder.presentation.viewmodel

sealed interface MainActivityState {
    class FabVisibility(val visible: Boolean) : MainActivityState
}
