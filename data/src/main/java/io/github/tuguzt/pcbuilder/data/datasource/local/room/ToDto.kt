package io.github.tuguzt.pcbuilder.data.datasource.local.room

import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ComponentDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ManufacturerDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.CaseDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.toEmbedded
import io.github.tuguzt.pcbuilder.domain.model.component.asMeasure
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData

/**
 * Converts [ComponentData] object to [ComponentDto] object.
 */
fun ComponentData.toEntity() = ComponentDto(
    componentId = "$id",
    name = name,
    description = description,
    weight = weight.asMeasure(),
    size = size,
    manufacturerId = manufacturer.id.toString(),
)

/**
 * Converts [ManufacturerData] object to [ManufacturerDto] object.
 */
fun ManufacturerData.toEntity(): ManufacturerDto = ManufacturerDto(
    manufacturerId = "$id",
    name = name,
    description = description,
)

/**
 * Converts [CaseData] object to [CaseDto] object.
 */
fun CaseData.toEntity(): CaseDto = CaseDto(
    caseId = "$id",
    driveBays = driveBays.toEmbedded(),
    expansionSlots = expansionSlots.toEmbedded(),
    powerSupply = powerSupply?.power,
    powerSupplyShroud = powerSupplyShroud,
    sidePanelWindow = sidePanelWindow,
    type = type,
)
