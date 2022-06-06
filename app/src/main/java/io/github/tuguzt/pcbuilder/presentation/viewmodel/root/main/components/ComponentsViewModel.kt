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
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class ComponentsViewModel @Inject constructor(
    private val componentRepository: ComponentRepository,
) : ViewModel() {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(ComponentsState())
    val uiState get() = _uiState

    private var updateJob: Job? = null

    init {
        updateComponents()
    }

    fun updateComponents() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            _uiState = uiState.copy(isUpdating = true)
            _uiState = when (val result = componentRepository.getAll()) {
                is Result.Error -> {
                    logger.error(result.throwable) { "Unknown error: ${result.error}" }
                    val message = UserMessage(ComponentsMessageKind.UnknownError)
                    val userMessages = uiState.userMessages + message
                    uiState.copy(userMessages = userMessages, isUpdating = false)
                }
                is Result.Success -> uiState.copy(components = result.data, isUpdating = false)
            }
        }
    }

    fun userMessageShown(messageId: NanoId) {
        val messages = uiState.userMessages.filterNot { it.id == messageId }
        _uiState = uiState.copy(userMessages = messages)
    }
}
