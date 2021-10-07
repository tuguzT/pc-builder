package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.cpu

import androidx.room.Embedded
import androidx.room.Relation
import io.github.tuguzt.pcbuilder.domain.model.component.cpu.CentralProcessingUnit
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object that links [components][ComponentDto]
 * and [central processing units][CentralProcessingUnitDto] together.
 *
 * Represents **one-to-one** relationship.
 *
 * @see ComponentDto
 * @see CentralProcessingUnitDto
 */
data class CentralProcessingUnitComponent(
    @Embedded private val component: ComponentDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "component_id",
    )
    private val centralProcessingUnit: CentralProcessingUnitDto,
) : CentralProcessingUnit {
    override val id get() = component.id
    override val name get() = component.name
    override val description get() = component.description
    override val weight get() = component.weight
    override val size get() = component.size
}

/**
 * Shorthand for central processing unit.
 *
 * @see CentralProcessingUnitComponent
 */
typealias CPUComponent = CentralProcessingUnitComponent
