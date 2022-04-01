package io.github.tuguzt.pcbuilder.presentation.repository.room

import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.model.component.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

fun ComponentDto.toData() = ComponentData(id, name, description, weight, size, imageUri)

fun Component.toData(): ComponentData = when (this) {
    is ComponentData -> this
    is ComponentDto -> toData()
    else -> {
        val className = this::class.simpleName
        throw IllegalStateException("Unable to convert $className to ComponentData")
    }
}
