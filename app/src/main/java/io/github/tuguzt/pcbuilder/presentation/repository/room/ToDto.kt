package io.github.tuguzt.pcbuilder.presentation.repository.room

import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.model.component.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

fun ComponentData.toDto() = ComponentDto(id, name, description, weight, size, imageUri)

fun Component.toDto(): ComponentDto = when (this) {
    is ComponentDto -> this
    is ComponentData -> toDto()
    else -> {
        val className = this::class.simpleName
        throw IllegalStateException("Unable to convert $className to ComponentDto")
    }
}
