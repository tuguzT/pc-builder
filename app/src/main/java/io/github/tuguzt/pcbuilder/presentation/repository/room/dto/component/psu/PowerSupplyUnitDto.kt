package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.psu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.component.psu.PowerSupplyUnit
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentChild
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object for [PowerSupplyUnit].
 *
 * @see PowerSupplyUnit
 */
@Entity(
    tableName = "psu",
    foreignKeys = [ForeignKey(
        entity = ComponentDto::class,
        parentColumns = ["id"],
        childColumns = ["component_id"],
        onDelete = ForeignKey.RESTRICT,
    )],
)
data class PowerSupplyUnitDto(
    @PrimaryKey
    @ColumnInfo(name = "component_id")
    override val id: String,
) : PowerSupplyUnit, ComponentChild

/**
 * Shorthand for power supply unit.
 *
 * @see PowerSupplyUnitDto
 */
typealias PSUDto = PowerSupplyUnitDto
