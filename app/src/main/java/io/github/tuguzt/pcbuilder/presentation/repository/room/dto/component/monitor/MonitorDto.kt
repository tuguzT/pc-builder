package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.monitor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.*
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentChild
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

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
        childColumns = ["component_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.RESTRICT,
    )],
)
data class MonitorDto(
    @PrimaryKey
    @ColumnInfo(name = "component_id")
    override val id: String,
) : Monitor, ComponentChild
