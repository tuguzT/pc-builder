package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.motherboard

import androidx.room.Embedded
import androidx.room.Relation
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.Motherboard
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object that links [components][ComponentDto]
 * and [motherboards][MotherboardDto] together.
 *
 * Represents **one-to-one** relationship.
 *
 * @see ComponentDto
 * @see MotherboardDto
 */
data class MotherboardComponent(
    @Embedded private val component: ComponentDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "component_id",
    )
    private val motherboard: MotherboardDto,
) : Motherboard {
    override val id get() = component.id
    override val name get() = component.name
    override val description get() = component.description
    override val weight get() = component.weight
    override val size get() = component.size
}
