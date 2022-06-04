package io.github.tuguzt.pcbuilder.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.*
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ComponentDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ManufacturerDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.CaseDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.CaseMotherboardFormFactorCrossRef
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.motherboard.MotherboardFormFactorDto

/**
 * Room database which contains all the data saved by user on the device.
 */
@Database(
    entities = [
        ComponentDto::class,
        ManufacturerDto::class,
        MotherboardFormFactorDto::class,
        CaseMotherboardFormFactorCrossRef::class,
        CaseDto::class,
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
