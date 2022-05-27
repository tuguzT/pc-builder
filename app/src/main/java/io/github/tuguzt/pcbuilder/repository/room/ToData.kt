package io.github.tuguzt.pcbuilder.repository.room

import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.ComponentData
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ComponentDto

/**
 * Converts [ComponentDto] object to [ComponentData] object.
 */
fun ComponentDto.toData() = ComponentData(id, name, description, weight, size)

/**
 * Converts [Component] object to [ComponentData] object.
 */
fun Component.toData(): ComponentData = when (this) {
    is ComponentData -> this
    is ComponentDto -> toData()
    else -> {
        val className = this::class.simpleName
        throw IllegalStateException("Unable to convert $className to ComponentData")
    }
}
