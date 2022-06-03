package io.github.tuguzt.pcbuilder.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.repository.Repository
import io.github.tuguzt.pcbuilder.repository.room.PCBuilderDatabase
import io.github.tuguzt.pcbuilder.repository.room.buildDatabase
import io.github.tuguzt.pcbuilder.repository.room.dao.ComponentDao
import io.github.tuguzt.pcbuilder.repository.room.dao.ManufacturerDao
import io.github.tuguzt.pcbuilder.repository.room.impl.RoomComponentRepository
import io.github.tuguzt.pcbuilder.repository.room.impl.RoomManufacturerRepository
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
    fun provideRoomManufacturerRepository(dao: ManufacturerDao) = RoomManufacturerRepository(dao)

    @Provides
    fun provideRoomComponentRepository(
        componentDao: ComponentDao,
        manufacturerRepository: RoomManufacturerRepository,
    ): Repository<NanoId, ComponentData> =
        RoomComponentRepository(componentDao, manufacturerRepository)
}
