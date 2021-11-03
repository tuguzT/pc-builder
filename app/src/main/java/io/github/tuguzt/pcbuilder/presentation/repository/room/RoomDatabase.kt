package io.github.tuguzt.pcbuilder.presentation.repository.room

import androidx.room.Database
import io.github.tuguzt.pcbuilder.presentation.repository.room.dao.ComponentDao
import io.github.tuguzt.pcbuilder.presentation.repository.room.dao.MonitorDao
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.build.BuildDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto
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
 * Room database which contains all the data locally saved by user.
 */
@Database(
    entities = [
        ComponentDto::class,
        CaseDto::class,
        CoolerDto::class,
        CentralProcessingUnitDto::class,
        GraphicsProcessingUnitDto::class,
        MemoryDto::class,
        MonitorDto::class,
        MotherboardDto::class,
        PowerSupplyUnitDto::class,
        StorageDto::class,
        BuildDto::class,
    ],
    version = 1,
    exportSchema = false,
)
internal abstract class RoomDatabase : androidx.room.RoomDatabase() {
    abstract val componentDao: ComponentDao
    abstract val monitorDao: MonitorDao
}
