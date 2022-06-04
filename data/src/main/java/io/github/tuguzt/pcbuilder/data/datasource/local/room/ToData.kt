package io.github.tuguzt.pcbuilder.data.datasource.local.room

import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ManufacturerDto
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData

/**
 * Converts [ManufacturerDto] object to [ManufacturerData] object.
 */
fun ManufacturerDto.toData(): ManufacturerData = ManufacturerData(NanoId(id), name, description)
