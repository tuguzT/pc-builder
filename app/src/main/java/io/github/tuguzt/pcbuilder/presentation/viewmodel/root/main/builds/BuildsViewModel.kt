package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.builds

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.repository.BuildRepository
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class BuildsViewModel @Inject constructor(
    private val buildRepository: BuildRepository,
) : ViewModel() {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(BuildsState())
    val uiState get() = _uiState

    private var updateJob: Job? = null

    init {
        updateBuilds()
    }

    private suspend fun updateBuildsNow() {
        _uiState = when (val result = buildRepository.getAll()) {
            is Result.Error -> {
                logger.error(result.throwable) { "Unknown error: ${result.error}" }
                val message = UserMessage(BuildsMessageKind.UnknownError)
                val userMessages = uiState.userMessages + message
                uiState.copy(userMessages = userMessages)
            }
            is Result.Success -> uiState.copy(builds = result.data)
        }
    }

    fun updateBuilds() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            _uiState = uiState.copy(isUpdating = true)
            updateBuildsNow()
            _uiState = uiState.copy(isUpdating = false)
        }
    }

    private var changeJob: Job? = null

    fun saveBuild(build: BuildData) {
        changeJob?.cancel()
        changeJob = viewModelScope.launch {
            _uiState = uiState.copy(isUpdating = true)
            _uiState = when (val result = buildRepository.save(build)) {
                is Result.Error -> {
                    logger.error(result.throwable) { "Unknown error: ${result.error}" }
                    val message = UserMessage(BuildsMessageKind.UnknownError)
                    val userMessages = uiState.userMessages + message
                    uiState.copy(userMessages = userMessages, isUpdating = false)
                }
                is Result.Success -> {
                    updateBuildsNow()
                    val message = UserMessage(BuildsMessageKind.BuildAdded)
                    val userMessages = uiState.userMessages + message
                    uiState.copy(userMessages = userMessages, isUpdating = false)
                }
            }
        }
    }

    fun deleteBuild(build: BuildData) {
        changeJob?.cancel()
        changeJob = viewModelScope.launch {
            _uiState = uiState.copy(isUpdating = true)
            _uiState = when (val result = buildRepository.delete(build)) {
                is Result.Error -> {
                    logger.error(result.throwable) { "Unknown error: ${result.error}" }
                    val message = UserMessage(BuildsMessageKind.UnknownError)
                    val userMessages = uiState.userMessages + message
                    uiState.copy(userMessages = userMessages, isUpdating = false)
                }
                is Result.Success -> {
                    updateBuildsNow()
                    val message = UserMessage(BuildsMessageKind.BuildDeleted)
                    val userMessages = uiState.userMessages + message
                    uiState.copy(userMessages = userMessages, isUpdating = false)
                }
            }
        }
    }

    fun userMessageShown(messageId: NanoId) {
        val messages = uiState.userMessages.filterNot { it.id == messageId }
        _uiState = uiState.copy(userMessages = messages)
    }
}
