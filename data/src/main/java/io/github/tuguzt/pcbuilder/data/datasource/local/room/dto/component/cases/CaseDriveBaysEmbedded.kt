package io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases

import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseDriveBays

data class CaseDriveBaysEmbedded(
    val external5_25_count: Int,
    val external3_5_count: Int,
    val internal3_5_count: Int,
    val internal2_5_count: Int,
)

fun CaseDriveBaysEmbedded.toDomain() = CaseDriveBays(
    external5_25_count = external5_25_count.toUInt(),
    external3_5_count = external3_5_count.toUInt(),
    internal3_5_count = internal3_5_count.toUInt(),
    internal2_5_count = internal2_5_count.toUInt(),
)

fun CaseDriveBays.toEmbedded() = CaseDriveBaysEmbedded(
    external5_25_count = external5_25_count.toInt(),
    external3_5_count = external3_5_count.toInt(),
    internal3_5_count = internal3_5_count.toInt(),
    internal2_5_count = internal2_5_count.toInt(),
)
