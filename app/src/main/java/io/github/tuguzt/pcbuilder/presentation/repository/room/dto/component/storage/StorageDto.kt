package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.component.storage.Storage
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentChild
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object for [Storage].
 *
 * @see Storage
 */
@Entity(
    tableName = "storage",
    foreignKeys = [ForeignKey(
        entity = ComponentDto::class,
        parentColumns = ["id"],
        childColumns = ["component_id"],
        onDelete = ForeignKey.RESTRICT,
    )],
)
data class StorageDto(
    @PrimaryKey
    @ColumnInfo(name = "component_id")
    override val id: String,
) : Storage, ComponentChild
