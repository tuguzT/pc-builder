package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.repository.ComponentRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.presentation.di.Local
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Injectable view model with list of components.
 */
@HiltViewModel
class ComponentsViewModel @Inject constructor(
    @Local private val localRepository: ComponentRepository,
) : ViewModel() {

    private var _uiState by mutableStateOf(ComponentsState())
    val uiState get() = _uiState

    private var updateJob: Job? = null

    init {
        updateComponents()
    }

    fun updateComponents() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            updateComponentsNow()
            _uiState = uiState.copy(isUpdating = false)
        }
    }

    private suspend fun updateComponentsNow() {
        _uiState = uiState.copy(isUpdating = true)
        _uiState = when (val result = localRepository.getAll()) {
            is Result.Error -> {
                val message = UserMessage(ComponentsMessageKind.UnknownError)
                val userMessages = uiState.userMessages + message
                uiState.copy(userMessages = userMessages)
            }
            is Result.Success -> uiState.copy(components = result.data)
        }
    }

    fun addComponent(component: ComponentData) {
        viewModelScope.launch {
            localRepository.save(component)
            updateComponentsNow()

            val message = UserMessage(ComponentsMessageKind.ComponentAdded)
            val userMessages = uiState.userMessages + message
            _uiState = uiState.copy(userMessages = userMessages, isUpdating = false)
        }
    }

    fun deleteComponent(component: ComponentData) {
        viewModelScope.launch {
            localRepository.delete(component)
            updateComponentsNow()

            val message = UserMessage(ComponentsMessageKind.ComponentDeleted)
            val userMessages = uiState.userMessages + message
            _uiState = uiState.copy(userMessages = userMessages, isUpdating = false)
        }
    }

    fun userMessageShown(messageId: NanoId) {
        val messages = uiState.userMessages.filterNot { it.id == messageId }
        _uiState = uiState.copy(userMessages = messages)
    }
}
