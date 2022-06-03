package io.github.tuguzt.pcbuilder.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.pcbuilder.data.repository.Repository
import io.github.tuguzt.pcbuilder.data.repository.room.PCBuilderDatabase
import io.github.tuguzt.pcbuilder.data.repository.room.buildDatabase
import io.github.tuguzt.pcbuilder.data.repository.room.dao.*
import io.github.tuguzt.pcbuilder.data.repository.room.impl.*
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun providePCBuilderDatabase(@ApplicationContext context: Context): PCBuilderDatabase =
        buildDatabase(context = context, name = "pc-builder")

    @Provides
    fun provideComponentDao(database: PCBuilderDatabase): ComponentDao = database.componentDao

    @Provides
    fun provideManufacturerDao(database: PCBuilderDatabase): ManufacturerDao =
        database.manufacturerDao

    @Provides
    fun provideMotherboardFormFactorDao(database: PCBuilderDatabase): MotherboardFormFactorDao =
        database.motherboardFormFactorDao

    @Provides
    fun provideCaseDao(database: PCBuilderDatabase): CaseDao = database.caseDao

    @Provides
    fun provideCaseMotherboardFormFactorCrossRefDao(database: PCBuilderDatabase): CaseMotherboardFormFactorCrossRefDao =
        database.caseMotherboardFormFactorCrossRefDao

    @Provides
    fun provideRoomManufacturerRepository(dao: ManufacturerDao): RoomManufacturerRepository =
        RoomManufacturerRepository(dao)

    @Provides
    fun provideRoomMotherboardFormFactorRepository(
        dao: MotherboardFormFactorDao,
    ): RoomMotherboardFormFactorRepository = RoomMotherboardFormFactorRepository(dao)

    @Provides
    fun provideRoomComponentRepository(
        componentDao: ComponentDao,
        manufacturerRepository: RoomManufacturerRepository,
    ): Repository<NanoId, ComponentData> =
        RoomComponentRepository(componentDao, manufacturerRepository)

    @Provides
    fun provideRoomCaseMotherboardFormFactorCrossRefRepository(
        dao: CaseMotherboardFormFactorCrossRefDao,
    ): RoomCaseMotherboardFormFactorCrossRefRepository =
        RoomCaseMotherboardFormFactorCrossRefRepository(dao)

    @Provides
    fun provideRoomCaseRepository(
        caseDao: CaseDao,
        componentRepository: Repository<NanoId, ComponentData>,
        motherboardFormFactorRepository: RoomMotherboardFormFactorRepository,
        caseMotherboardFormFactorCrossRefRepository: RoomCaseMotherboardFormFactorCrossRefRepository,
    ): Repository<NanoId, CaseData> =
        RoomCaseRepository(
            caseDao,
            componentRepository as RoomComponentRepository,
            motherboardFormFactorRepository,
            caseMotherboardFormFactorCrossRefRepository,
        )
}
