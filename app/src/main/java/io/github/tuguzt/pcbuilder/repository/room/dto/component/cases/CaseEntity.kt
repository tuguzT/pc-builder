package io.github.tuguzt.pcbuilder.repository.room.dto.component.cases

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CasePowerSupplyShroud
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseSidePanelWindow
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseType
import io.github.tuguzt.pcbuilder.domain.model.units.Power
import io.github.tuguzt.pcbuilder.repository.room.converters.CaseTypeConverter
import io.github.tuguzt.pcbuilder.repository.room.converters.PowerConverter
import io.nacular.measured.units.Measure

@Entity(tableName = "cases")
@TypeConverters(PowerConverter::class, CaseTypeConverter::class)
data class CaseEntity(
    @PrimaryKey val caseId: String,
    @Embedded val driveBays: CaseDriveBaysEmbedded,
    @Embedded val expansionSlots: CaseExpansionSlotsEmbedded,
    val powerSupply: Measure<Power>?,
    val powerSupplyShroud: CasePowerSupplyShroud,
    val sidePanelWindow: CaseSidePanelWindow?,
    val type: CaseType,
) : Identifiable<String> {
    override val id: String get() = caseId
}
