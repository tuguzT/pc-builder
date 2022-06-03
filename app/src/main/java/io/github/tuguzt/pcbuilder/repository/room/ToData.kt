package io.github.tuguzt.pcbuilder.repository.room

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ManufacturerEntity

/**
 * Converts [ManufacturerEntity] object to [ManufacturerData] object.
 */
fun ManufacturerEntity.toData(): ManufacturerData = ManufacturerData(NanoId(id), name, description)
