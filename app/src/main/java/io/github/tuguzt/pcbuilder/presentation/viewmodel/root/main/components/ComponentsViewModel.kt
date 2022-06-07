package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.repository.ComponentRepository
import io.github.tuguzt.pcbuilder.data.repository.UsersRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class ComponentsViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
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

    private suspend fun updateComponentsNow() {
        _uiState = when (val result = componentRepository.getAll()) {
            is Result.Error -> {
                logger.error(result.throwable) { "Unknown error: ${result.error}" }
                val message = UserMessage(ComponentsMessageKind.UnknownError)
                val userMessages = uiState.userMessages + message
                uiState.copy(userMessages = userMessages)
            }
            is Result.Success -> uiState.copy(components = result.data)
        }
    }

    fun updateComponents() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            _uiState = uiState.copy(isUpdating = true)
            updateComponentsNow()
            _uiState = uiState.copy(isUpdating = false)
        }
    }

    fun updateFavorites(component: PolymorphicComponent, isFavorite: Boolean) {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            _uiState = uiState.copy(isUpdating = false)
            when {
                isFavorite -> usersRepository.addToFavorites(component.id)
                else -> usersRepository.removeFromFavorites(component.id)
            }
            updateComponentsNow()
        }
    }

    fun userMessageShown(messageId: NanoId) {
        val messages = uiState.userMessages.filterNot { it.id == messageId }
        _uiState = uiState.copy(userMessages = messages)
    }
}
