package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.monitor

import androidx.room.Embedded
import androidx.room.Relation
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.Monitor
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto

/**
 * Data Transfer Object that links [components][ComponentDto] and [monitors][MonitorDto] together.
 *
 * Represents **one-to-one** relationship.
 *
 * @see ComponentDto
 * @see MonitorDto
 */
data class MonitorComponents(
    @Embedded private val monitor: MonitorDto,
    @Relation(parentColumn = "id", entityColumn = "id") private val component: ComponentDto,
) : Monitor {
    override val id = component.id
    override val name = component.name
    override val description = component.description
    override val weight = component.weight
    override val size = component.size
}
