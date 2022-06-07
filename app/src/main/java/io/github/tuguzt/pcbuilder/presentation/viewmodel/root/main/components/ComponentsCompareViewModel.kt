package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class ComponentsCompareViewModel @Inject constructor() : ViewModel() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(CompareComponentsState())
    val uiState get() = _uiState

    fun updateFirstComponent(component: PolymorphicComponent?) {
        if (component != null && uiState.secondComponent != null && component.javaClass != uiState.secondComponent?.javaClass) {
            val userMessage = UserMessage(CompareComponentsMessageKind.DifferentTypes)
            val userMessages = uiState.userMessages + userMessage
            _uiState = uiState.copy(userMessages = userMessages)
            return
        }
        if (component == uiState.secondComponent && uiState.secondComponent != null) {
            val userMessage = UserMessage(CompareComponentsMessageKind.EqualComponents)
            val userMessages = uiState.userMessages + userMessage
            _uiState = uiState.copy(userMessages = userMessages)
            return
        }
        _uiState = uiState.copy(firstComponent = component)
    }

    fun updateSecondComponent(component: PolymorphicComponent?) {
        if (component != null && uiState.firstComponent != null && component.javaClass != uiState.firstComponent?.javaClass) {
            val userMessage = UserMessage(CompareComponentsMessageKind.DifferentTypes)
            val userMessages = uiState.userMessages + userMessage
            _uiState = uiState.copy(userMessages = userMessages)
            return
        }
        if (component == uiState.firstComponent && uiState.firstComponent != null) {
            val userMessage = UserMessage(CompareComponentsMessageKind.EqualComponents)
            val userMessages = uiState.userMessages + userMessage
            _uiState = uiState.copy(userMessages = userMessages)
            return
        }
        _uiState = uiState.copy(secondComponent = component)
    }

    fun userMessageShown(messageId: NanoId) {
        val messages = uiState.userMessages.filterNot { it.id == messageId }
        _uiState = uiState.copy(userMessages = messages)
    }
}
