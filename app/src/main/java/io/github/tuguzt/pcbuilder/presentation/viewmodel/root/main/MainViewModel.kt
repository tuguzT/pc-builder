package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.presentation.view.navigation.Destination
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(MainState())
    val uiState get() = _uiState

    fun updateTitle(title: String) {
        _uiState = uiState.copy(title = title)
    }

    fun updateCurrentDestination(currentDestination: Destination) {
        logger.info { currentDestination }
        _uiState = uiState.copy(currentDestination = currentDestination)
    }

    fun updateOnNavigateUpAction(onNavigateUpAction: () -> Unit) {
        _uiState = uiState.copy(onNavigateUpAction = onNavigateUpAction)
    }
}
