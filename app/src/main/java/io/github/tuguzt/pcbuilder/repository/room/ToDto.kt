package io.github.tuguzt.pcbuilder.repository.room

import io.github.tuguzt.pcbuilder.domain.model.component.asMeasure
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ComponentEntity
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ManufacturerEntity

/**
 * Converts [ComponentData] object to [ComponentEntity] object.
 */
fun ComponentData.toEntity() = ComponentEntity(
    componentId = "$id",
    name = name,
    description = description,
    weight = weight.asMeasure(),
    size = size,
    manufacturerId = manufacturer.id.toString(),
)

/**
 * Converts [ManufacturerData] object to [ManufacturerEntity] object.
 */
fun ManufacturerData.toEntity(): ManufacturerEntity = ManufacturerEntity("$id", name, description)
