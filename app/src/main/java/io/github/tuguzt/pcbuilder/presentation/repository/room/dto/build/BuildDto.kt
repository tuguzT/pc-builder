package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.build

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.domain.model.build.Build
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto

/**
 * Data Transfer Object for [Build].
 *
 * @see Build
 */
@Entity(
    tableName = "build",
    foreignKeys = [ForeignKey(
        entity = ComponentDto::class,
        parentColumns = ["id"],
        childColumns = ["id"],
        onDelete = ForeignKey.CASCADE,
    )]
)
data class BuildDto(
    @PrimaryKey override val id: String,
    val name: String,
    @ColumnInfo(name = "case_id") val caseId: String?,
    @ColumnInfo(name = "cooler_id") val coolerId: String?,
    @ColumnInfo(name = "cpu_id") val centralProcessingUnitId: String?,
    @ColumnInfo(name = "gpu_id") val graphicsProcessingUnitId: String?,
    @ColumnInfo(name = "memory_id") val memoryId: String?,
    @ColumnInfo(name = "monitor_id") val monitorId: String?,
    @ColumnInfo(name = "motherboard_id") val motherboardId: String?,
    @ColumnInfo(name = "psu_id") val powerSupplyUnitId: String?,
    @ColumnInfo(name = "storage_id") val storageId: String?,
) : Identifiable<String>
