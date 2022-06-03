package io.github.tuguzt.pcbuilder.data.repository.room

import io.github.tuguzt.pcbuilder.data.repository.room.dto.component.ComponentEntity
import io.github.tuguzt.pcbuilder.data.repository.room.dto.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.data.repository.room.dto.component.cases.CaseEntity
import io.github.tuguzt.pcbuilder.data.repository.room.dto.component.cases.toEmbedded
import io.github.tuguzt.pcbuilder.domain.model.component.asMeasure
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData

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
fun ManufacturerData.toEntity(): ManufacturerEntity = ManufacturerEntity(
    manufacturerId = "$id",
    name = name,
    description = description,
)

/**
 * Converts [CaseData] object to [CaseEntity] object.
 */
fun CaseData.toEntity(): CaseEntity = CaseEntity(
    caseId = "$id",
    driveBays = driveBays.toEmbedded(),
    expansionSlots = expansionSlots.toEmbedded(),
    powerSupply = powerSupply?.power,
    powerSupplyShroud = powerSupplyShroud,
    sidePanelWindow = sidePanelWindow,
    type = type,
)
