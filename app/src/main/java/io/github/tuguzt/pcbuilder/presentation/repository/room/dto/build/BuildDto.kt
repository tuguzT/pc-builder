package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.build

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.domain.model.build.Build
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.cases.CaseDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.cooler.CoolerDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.cpu.CentralProcessingUnitDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.gpu.GraphicsProcessingUnitDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.memory.MemoryDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.monitor.MonitorDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.motherboard.MotherboardDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.psu.PowerSupplyUnitDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.storage.StorageDto

/**
 * Data Transfer Object for [Build].
 *
 * @see Build
 */
@Entity(
    tableName = "build",
    foreignKeys = [
        ForeignKey(
            entity = CaseDto::class,
            parentColumns = ["component_id"],
            childColumns = ["case_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = CoolerDto::class,
            parentColumns = ["component_id"],
            childColumns = ["cooler_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = CentralProcessingUnitDto::class,
            parentColumns = ["component_id"],
            childColumns = ["cpu_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = GraphicsProcessingUnitDto::class,
            parentColumns = ["component_id"],
            childColumns = ["gpu_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = MemoryDto::class,
            parentColumns = ["component_id"],
            childColumns = ["memory_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = MonitorDto::class,
            parentColumns = ["component_id"],
            childColumns = ["monitor_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = MotherboardDto::class,
            parentColumns = ["component_id"],
            childColumns = ["motherboard_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = PowerSupplyUnitDto::class,
            parentColumns = ["component_id"],
            childColumns = ["psu_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = StorageDto::class,
            parentColumns = ["component_id"],
            childColumns = ["storage_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
    ],
)
data class BuildDto(
    @PrimaryKey override val id: String,
    val name: String,
    @ColumnInfo(name = "case_id", index = true) val caseId: String?,
    @ColumnInfo(name = "cooler_id", index = true) val coolerId: String?,
    @ColumnInfo(name = "cpu_id", index = true) val centralProcessingUnitId: String?,
    @ColumnInfo(name = "gpu_id", index = true) val graphicsProcessingUnitId: String?,
    @ColumnInfo(name = "memory_id", index = true) val memoryId: String?,
    @ColumnInfo(name = "monitor_id", index = true) val monitorId: String?,
    @ColumnInfo(name = "motherboard_id", index = true) val motherboardId: String?,
    @ColumnInfo(name = "psu_id", index = true) val powerSupplyUnitId: String?,
    @ColumnInfo(name = "storage_id", index = true) val storageId: String?,
) : Identifiable<String>
