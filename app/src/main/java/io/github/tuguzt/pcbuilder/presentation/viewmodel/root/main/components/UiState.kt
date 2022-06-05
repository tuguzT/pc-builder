package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components

import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageState
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage

data class ComponentsState(
    val components: List<ComponentData> = listOf(),
    val isUpdating: Boolean = true,
    override val userMessages: List<UserMessage<ComponentsMessageKind>> = listOf(),
) : MessageState<ComponentsMessageKind>

enum class ComponentsMessageKind : MessageKind {
    ComponentAdded,
    ComponentDeleted,
    UnknownError,
}

data class RemoteComponentsState(
    val components: List<ComponentData> = listOf(),
    val isUpdating: Boolean = true,
    override val userMessages: List<UserMessage<RemoteComponentsMessageKind>> = listOf(),
) : MessageState<RemoteComponentsMessageKind>

enum class RemoteComponentsMessageKind : MessageKind {
    UnknownError,
}
