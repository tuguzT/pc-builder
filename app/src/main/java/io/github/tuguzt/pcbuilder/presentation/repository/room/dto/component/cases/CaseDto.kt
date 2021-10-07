package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.cases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.component.cases.Case
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentChild
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Transfer Object for [Case].
 *
 * @see Case
 */
@Entity(
    tableName = "case",
    foreignKeys = [ForeignKey(
        entity = ComponentDto::class,
        parentColumns = ["id"],
        childColumns = ["component_id"],
        onDelete = ForeignKey.RESTRICT,
    )],
)
data class CaseDto(
    @PrimaryKey
    @ColumnInfo(name = "component_id")
    override val id: String,
) : Case, ComponentChild
