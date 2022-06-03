package io.github.tuguzt.pcbuilder.data.repository.room.dto.component.motherboard

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor

@Entity(tableName = "motherboard_form_factor")
data class MotherboardFormFactorEntity(
    @PrimaryKey val motherboardFormFactorId: MotherboardFormFactor,
) : Identifiable<MotherboardFormFactor> {
    override val id: MotherboardFormFactor get() = motherboardFormFactorId
}
