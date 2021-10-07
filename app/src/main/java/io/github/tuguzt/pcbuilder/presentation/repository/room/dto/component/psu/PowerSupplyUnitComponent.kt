package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.psu

import androidx.room.Embedded
import androidx.room.Relation
import io.github.tuguzt.pcbuilder.domain.model.component.psu.PowerSupplyUnit
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object that links [components][ComponentDto]
 * and [power supply units][PowerSupplyUnitDto] together.
 *
 * Represents **one-to-one** relationship.
 *
 * @see ComponentDto
 * @see PowerSupplyUnitDto
 */
data class PowerSupplyUnitComponent(
    @Embedded private val component: ComponentDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "component_id",
    )
    private val powerSupplyUnit: PowerSupplyUnitDto,
) : PowerSupplyUnit {
    override val id get() = component.id
    override val name get() = component.name
    override val description get() = component.description
    override val weight get() = component.weight
    override val size get() = component.size
}

/**
 * Shorthand for power supply unit.
 *
 * @see PowerSupplyUnitDto
 */
typealias PSUComponent = PowerSupplyUnitComponent
