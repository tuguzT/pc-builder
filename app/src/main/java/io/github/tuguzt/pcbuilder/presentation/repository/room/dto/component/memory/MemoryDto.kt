package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.memory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.component.memory.Memory
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentChild
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object for [Memory].
 *
 * @see Memory
 */
@Entity(
    tableName = "memory",
    foreignKeys = [ForeignKey(
        entity = ComponentDto::class,
        parentColumns = ["id"],
        childColumns = ["component_id"],
        onDelete = ForeignKey.RESTRICT,
    )],
)
data class MemoryDto(
    @PrimaryKey
    @ColumnInfo(name = "component_id")
    override val id: String,
) : Memory, ComponentChild
