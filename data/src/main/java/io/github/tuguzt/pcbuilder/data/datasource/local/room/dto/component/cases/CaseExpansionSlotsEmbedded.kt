package io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases

import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseExpansionSlots

data class CaseExpansionSlotsEmbedded(
    val fullHeightCount: Int,
    val halfHeightCount: Int,
)

fun CaseExpansionSlotsEmbedded.toDomain() = CaseExpansionSlots(
    fullHeightCount = fullHeightCount.toUInt(),
    halfHeightCount = halfHeightCount.toUInt(),
)

fun CaseExpansionSlots.toEmbedded() = CaseExpansionSlotsEmbedded(
    fullHeightCount = fullHeightCount.toInt(),
    halfHeightCount = halfHeightCount.toInt(),
)
