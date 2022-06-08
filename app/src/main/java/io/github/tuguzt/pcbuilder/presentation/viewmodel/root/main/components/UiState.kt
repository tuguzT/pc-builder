package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components

import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageState
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage

data class ComponentsState(
    val components: List<PolymorphicComponent> = listOf(),
    val isUpdating: Boolean = true,
    override val userMessages: List<UserMessage<out ComponentsMessageKind>> = listOf(),
) : MessageState<ComponentsMessageKind>

inline val ComponentsState.favoriteComponents: List<PolymorphicComponent>
    get() = components.filter(PolymorphicComponent::isFavorite)

enum class ComponentsMessageKind : MessageKind {
    ComponentAdded,
    ComponentDeleted,
    UnknownError,
}

data class CompareComponentsState(
    val firstComponent: PolymorphicComponent? = null,
    val secondComponent: PolymorphicComponent? = null,
    override val userMessages: List<UserMessage<out CompareComponentsMessageKind>> = listOf(),
) : MessageState<CompareComponentsMessageKind>

enum class CompareComponentsMessageKind : MessageKind {
    DifferentTypes, EqualComponents,
}

enum class ComponentToChoose {
    First, Second,
}
