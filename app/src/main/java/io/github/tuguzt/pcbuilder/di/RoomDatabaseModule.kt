package io.github.tuguzt.pcbuilder.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.repository.Repository
import io.github.tuguzt.pcbuilder.repository.room.PCBuilderDatabase
import io.github.tuguzt.pcbuilder.repository.room.buildDatabase
import io.github.tuguzt.pcbuilder.repository.room.dao.ComponentDao
import io.github.tuguzt.pcbuilder.repository.room.impl.RoomComponentRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {
    @Provides
    @Singleton
    fun providePCBuilderDatabase(@ApplicationContext appContext: Context): PCBuilderDatabase =
        buildDatabase(context = appContext, name = "pc-builder")

    @Provides
    fun provideComponentDao(database: PCBuilderDatabase): ComponentDao = database.componentDao

    @Provides
    fun provideRoomComponentRepository(dao: ComponentDao): Repository<String, Component> =
        RoomComponentRepository(dao)
}
