package io.github.tuguzt.pcbuilder.data.repository.room

import io.github.tuguzt.pcbuilder.data.repository.room.dto.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData

/**
 * Converts [ManufacturerEntity] object to [ManufacturerData] object.
 */
fun ManufacturerEntity.toData(): ManufacturerData = ManufacturerData(NanoId(id), name, description)
