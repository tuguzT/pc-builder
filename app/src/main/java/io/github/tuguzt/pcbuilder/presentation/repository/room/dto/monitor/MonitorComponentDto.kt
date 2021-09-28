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
data class MonitorComponentDto(
    @Embedded private val component: ComponentDto,
    @Relation(parentColumn = "id", entityColumn = "id") private val monitor: MonitorDto,
) : Monitor {
    val id = component.id
    override val name = component.name
    override val description = component.description
    override val weight = component.weight
    override val size = component.size
    override val screenSize = monitor.screenSize
    override val resolution = monitor.resolution
    override val refreshRate = monitor.refreshRate
    override val responseTime = monitor.responseTime
    override val aspectRatio = monitor.aspectRatio
    override val panelType = monitor.panelType
    override val luminance = monitor.luminance
    override val screenType = monitor.screenType
    override val contrastRatio = monitor.contrastRatio
    override val monitorInterface = monitor.monitorInterface
    override val pwmType = monitor.pwmType
    override val frameSyncType = monitor.frameSyncType
}
