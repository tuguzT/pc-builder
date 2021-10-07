package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.cpu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.component.cpu.CentralProcessingUnit
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentChild
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object for [CentralProcessingUnit].
 *
 * @see CentralProcessingUnit
 */
@Entity(
    tableName = "cpu",
    foreignKeys = [ForeignKey(
        entity = ComponentDto::class,
        parentColumns = ["id"],
        childColumns = ["component_id"],
        onDelete = ForeignKey.RESTRICT,
    )],
)
data class CentralProcessingUnitDto(
    @PrimaryKey
    @ColumnInfo(name = "component_id")
    override val id: String,
) : CentralProcessingUnit, ComponentChild

/**
 * Shorthand for central processing unit.
 *
 * @see CentralProcessingUnitDto
 */
typealias CPUDto = CentralProcessingUnitDto
