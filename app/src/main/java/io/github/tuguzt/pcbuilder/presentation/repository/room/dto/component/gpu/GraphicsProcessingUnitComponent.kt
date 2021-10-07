package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.gpu

import androidx.room.Embedded
import androidx.room.Relation
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.GraphicsProcessingUnit
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object that links [components][ComponentDto]
 * and [graphics processing units][GraphicsProcessingUnitDto] together.
 *
 * Represents **one-to-one** relationship.
 *
 * @see ComponentDto
 * @see GraphicsProcessingUnitDto
 */
data class GraphicsProcessingUnitComponent(
    @Embedded private val component: ComponentDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "component_id",
    )
    private val graphicsProcessingUnit: GraphicsProcessingUnitDto,
) : GraphicsProcessingUnit {
    override val id get() = component.id
    override val name get() = component.name
    override val description get() = component.description
    override val weight get() = component.weight
    override val size get() = component.size
}

/**
 * Shorthand for graphics processing unit.
 *
 * @see GraphicsProcessingUnitComponent
 */
typealias GPUComponent = GraphicsProcessingUnitComponent
