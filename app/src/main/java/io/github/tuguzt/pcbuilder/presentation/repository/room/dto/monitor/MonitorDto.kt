package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.monitor

import androidx.room.*
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.*
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentChild
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto

/**
 * Data Transfer Object for [Monitor].
 *
 * @see Monitor
 */
@Entity(
    tableName = "monitor",
    foreignKeys = [ForeignKey(
        entity = ComponentDto::class,
        parentColumns = ["id"],
        childColumns = ["id"],
        onDelete = ForeignKey.CASCADE,
    )],
)
data class MonitorDto(override val id: String) : Monitor, ComponentChild
