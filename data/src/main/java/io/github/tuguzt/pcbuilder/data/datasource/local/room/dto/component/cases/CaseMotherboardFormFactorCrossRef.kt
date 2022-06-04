package io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases

import androidx.room.Entity
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor

@Entity(primaryKeys = ["caseId", "motherboardFormFactorId"])
data class CaseMotherboardFormFactorCrossRef(
    val caseId: String,
    val motherboardFormFactorId: MotherboardFormFactor,
) : Identifiable<CaseMotherboardFormFactorCrossRef> {
    override val id: CaseMotherboardFormFactorCrossRef get() = this
}
