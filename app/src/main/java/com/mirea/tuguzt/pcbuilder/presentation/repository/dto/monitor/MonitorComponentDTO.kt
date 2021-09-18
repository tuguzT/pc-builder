package com.mirea.tuguzt.pcbuilder.presentation.repository.dto.monitor

import androidx.room.Embedded
import androidx.room.Relation
import com.mirea.tuguzt.pcbuilder.domain.model.monitor.Monitor
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO

/**
 * Data Transfer Object that links [components][ComponentDTO] and [monitors][MonitorDTO] together.
 *
 * Represents **one-to-one** relationship.
 *
 * @see ComponentDTO
 * @see MonitorDTO
 */
data class MonitorComponentDTO(
    @Embedded private val component: ComponentDTO,
    @Relation(parentColumn = "id", entityColumn = "id") private val monitor: MonitorDTO,
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
