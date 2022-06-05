package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.data.repository.ComponentRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Injectable view model with list of components.
 */
@HiltViewModel
class ComponentsViewModel @Inject constructor(
    private val componentRepository: ComponentRepository,
) : ViewModel() {

    init {
        updateComponents()
    }

    private var _uiState by mutableStateOf(ComponentsState())
    val uiState get() = _uiState

    private var updateJob: Job? = null

    fun updateComponents() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch { updateComponentsNow() }
    }

    private suspend fun updateComponentsNow() {
        val components = componentRepository.getAll()
        _uiState = uiState.copy(components = components)
    }

    fun addComponent(component: ComponentData) {
        viewModelScope.launch {
            componentRepository.save(component)
            updateComponentsNow()

            val message = UserMessage(ComponentsMessageKind.ComponentAdded)
            val userMessages = uiState.userMessages + message
            _uiState = uiState.copy(userMessages = userMessages)
        }
    }

    fun deleteComponent(component: ComponentData) {
        viewModelScope.launch {
            componentRepository.delete(component)
            updateComponentsNow()

            val message = UserMessage(ComponentsMessageKind.ComponentDeleted)
            val userMessages = uiState.userMessages + message
            _uiState = uiState.copy(userMessages = userMessages)
        }
    }

    fun userMessageShown(messageId: NanoId) {
        val messages = uiState.userMessages.filterNot { it.id == messageId }
        _uiState = uiState.copy(userMessages = messages)
    }
}
