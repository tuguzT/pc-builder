package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.cooler

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.component.cooler.Cooler
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentChild
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object for [Cooler].
 *
 * @see Cooler
 */
@Entity(
    tableName = "cooler",
    foreignKeys = [ForeignKey(
        entity = ComponentDto::class,
        parentColumns = ["id"],
        childColumns = ["component_id"],
        onDelete = ForeignKey.RESTRICT,
    )],
)
data class CoolerDto(
    @PrimaryKey
    @ColumnInfo(name = "component_id")
    override val id: String,
) : Cooler, ComponentChild
