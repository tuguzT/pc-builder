package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.cooler

import androidx.room.Embedded
import androidx.room.Relation
import io.github.tuguzt.pcbuilder.domain.model.component.cooler.Cooler
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object that links [components][ComponentDto] and [coolers][CoolerDto] together.
 *
 * Represents **one-to-one** relationship.
 *
 * @see ComponentDto
 * @see CoolerDto
 */
data class CoolerComponent(
    @Embedded private val component: ComponentDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "component_id",
    )
    private val cooler: CoolerDto,
) : Cooler {
    override val id get() = component.id
    override val name get() = component.name
    override val description get() = component.description
    override val weight get() = component.weight
    override val size get() = component.size
}
