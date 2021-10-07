package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.motherboard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.Motherboard
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentChild
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object for [Motherboard].
 *
 * @see Motherboard
 */
@Entity(
    tableName = "motherboard",
    foreignKeys = [ForeignKey(
        entity = ComponentDto::class,
        parentColumns = ["id"],
        childColumns = ["component_id"],
        onDelete = ForeignKey.RESTRICT,
    )],
)
data class MotherboardDto(
    @PrimaryKey
    @ColumnInfo(name = "component_id")
    override val id: String,
) : Motherboard, ComponentChild
