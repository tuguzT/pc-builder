package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.gpu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.GraphicsProcessingUnit
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentChild
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object for [GraphicsProcessingUnit].
 *
 * @see GraphicsProcessingUnit
 */
@Entity(
    tableName = "gpu",
    foreignKeys = [ForeignKey(
        entity = ComponentDto::class,
        parentColumns = ["id"],
        childColumns = ["component_id"],
        onDelete = ForeignKey.RESTRICT,
    )],
)
data class GraphicsProcessingUnitDto(
    @PrimaryKey
    @ColumnInfo(name = "component_id")
    override val id: String,
) : GraphicsProcessingUnit, ComponentChild

/**
 * Shorthand for graphics processing unit.
 *
 * @see GraphicsProcessingUnitDto
 */
typealias GPUDto = GraphicsProcessingUnitDto
