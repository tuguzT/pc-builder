package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.components

import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage

data class ComponentsState(
    val components: List<ComponentData> = listOf(),
    val userMessages: List<UserMessage> = listOf(),
)
