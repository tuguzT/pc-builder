package io.github.tuguzt.pcbuilder.presentation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.pcbuilder.data.datasource.local.impl.LocalCaseMotherboardFormFactorCrossRefDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.impl.LocalManufacturerDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.impl.LocalMotherboardFormFactorDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.room.PCBuilderDatabase
import io.github.tuguzt.pcbuilder.data.datasource.local.room.buildDatabase
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.*
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
    fun provideLocalManufacturerDataSource(dao: ManufacturerDao): LocalManufacturerDataSource =
        LocalManufacturerDataSource(dao)

    @Provides
    fun provideLocalMotherboardFormFactorDataSource(dao: MotherboardFormFactorDao): LocalMotherboardFormFactorDataSource =
        LocalMotherboardFormFactorDataSource(dao)

    @Provides
    fun provideLocalCaseMotherboardFormFactorCrossRefDataSource(
        dao: CaseMotherboardFormFactorCrossRefDao,
    ): LocalCaseMotherboardFormFactorCrossRefDataSource =
        LocalCaseMotherboardFormFactorCrossRefDataSource(dao)
}
