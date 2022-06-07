package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components

import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageState
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage

data class ComponentsState(
    val components: List<PolymorphicComponent> = listOf(),
    val isUpdating: Boolean = true,
    override val userMessages: List<UserMessage<ComponentsMessageKind>> = listOf(),
) : MessageState<ComponentsMessageKind>

val ComponentsState.favoriteComponents: List<PolymorphicComponent>
    get() = components.filter(PolymorphicComponent::isFavorite)

enum class ComponentsMessageKind : MessageKind {
    ComponentAdded,
    ComponentDeleted,
    UnknownError,
}
