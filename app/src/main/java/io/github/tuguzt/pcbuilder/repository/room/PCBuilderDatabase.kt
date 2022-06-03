package io.github.tuguzt.pcbuilder.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.tuguzt.pcbuilder.repository.room.dao.*
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ComponentEntity
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.repository.room.dto.component.cases.CaseEntity
import io.github.tuguzt.pcbuilder.repository.room.dto.component.cases.CaseMotherboardFormFactorCrossRef
import io.github.tuguzt.pcbuilder.repository.room.dto.component.motherboard.MotherboardFormFactorEntity

/**
 * Room database which contains all the data saved by user on the device.
 */
@Database(
    entities = [
        ComponentEntity::class,
        ManufacturerEntity::class,
        MotherboardFormFactorEntity::class,
        CaseMotherboardFormFactorCrossRef::class,
        CaseEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class PCBuilderDatabase : RoomDatabase() {
    abstract val componentDao: ComponentDao
    abstract val manufacturerDao: ManufacturerDao
    abstract val motherboardFormFactorDao: MotherboardFormFactorDao
    abstract val caseMotherboardFormFactorCrossRefDao: CaseMotherboardFormFactorCrossRefDao
    abstract val caseDao: CaseDao
}
