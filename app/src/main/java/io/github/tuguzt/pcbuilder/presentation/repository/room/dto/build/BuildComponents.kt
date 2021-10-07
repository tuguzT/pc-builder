package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.build

import androidx.room.Embedded
import androidx.room.Relation
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
 * Data Transfer Object that links [builds][BuildDto] and its components together.
 *
 * Represents **one-to-one** relationship.
 *
 * @see BuildDto
 */
data class BuildComponents(
    @Embedded private val build: BuildDto,
    @Relation(
        parentColumn = "case_id",
        entityColumn = "component_id",
    )
    override val case: CaseDto?,
    @Relation(
        parentColumn = "cooler_id",
        entityColumn = "component_id",
    )
    override val cooler: CoolerDto?,
    @Relation(
        parentColumn = "cpu_id",
        entityColumn = "component_id",
    )
    override val centralProcessingUnit: CentralProcessingUnitDto?,
    @Relation(
        parentColumn = "gpu_id",
        entityColumn = "component_id",
    )
    override val graphicsProcessingUnit: GraphicsProcessingUnitDto?,
    @Relation(
        parentColumn = "memory_id",
        entityColumn = "component_id",
    )
    override val memory: MemoryDto?,
    @Relation(
        parentColumn = "monitor_id",
        entityColumn = "component_id",
    )
    override val monitor: MonitorDto?,
    @Relation(
        parentColumn = "motherboard_id",
        entityColumn = "component_id",
    )
    override val motherboard: MotherboardDto?,
    @Relation(
        parentColumn = "psu_id",
        entityColumn = "component_id",
    )
    override val powerSupplyUnit: PowerSupplyUnitDto?,
    @Relation(
        parentColumn = "storage_id",
        entityColumn = "component_id",
    )
    override val storage: StorageDto?,
) : Build {
    override val id: String get() = build.id
    override val name: String get() = build.name
}
