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
import io.github.tuguzt.pcbuilder.presentation.di.Remote
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteSearchComponentsViewModel @Inject constructor(
    @Remote private val remoteRepository: ComponentRepository,
) : ViewModel() {

    private var _uiState by mutableStateOf(RemoteComponentsState())
    val uiState get() = _uiState

    private var updateJob: Job? = null

    init {
        updateComponents()
    }

    fun updateComponents() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            _uiState = uiState.copy(isUpdating = true)
            val components = when (val result = remoteRepository.getAll()) {
                is Result.Success -> result.data
                is Result.Error -> {
                    val message = UserMessage(RemoteComponentsMessageKind.UnknownError)
                    val userMessages = uiState.userMessages + message
                    _uiState = uiState.copy(userMessages = userMessages, isUpdating = false)
                    return@launch
                }
            }
            _uiState = uiState.copy(components = components, isUpdating = false)
        }
    }

    fun userMessageShown(messageId: NanoId) {
        val messages = uiState.userMessages.filterNot { it.id == messageId }
        _uiState = uiState.copy(userMessages = messages)
    }
}
