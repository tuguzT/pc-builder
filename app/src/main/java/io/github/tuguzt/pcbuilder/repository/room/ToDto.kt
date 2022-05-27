package io.github.tuguzt.pcbuilder.repository.room

import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.ComponentData
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ComponentDto

/**
 * Converts [ComponentData] object to [ComponentDto] object.
 */
fun ComponentData.toDto() = ComponentDto(id, name, description, weight, size, imageUri = null)

/**
 * Converts [Component] object to [ComponentDto] object.
 */
fun Component.toDto(): ComponentDto = when (this) {
    is ComponentDto -> this
    is ComponentData -> toDto()
    else -> {
        val className = this::class.simpleName
        throw IllegalStateException("Unable to convert $className to ComponentDto")
    }
}
