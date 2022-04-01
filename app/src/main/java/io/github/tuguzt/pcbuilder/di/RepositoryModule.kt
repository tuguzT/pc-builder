package io.github.tuguzt.pcbuilder.di

import android.content.Context
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.repository.room.Database
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.buildDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Repository module of the Koin DI.
 */
val repositoryModule = module {
    single { database(androidContext()) }
    single { componentRepository(get()) }
}

private fun database(context: Context): Database = buildDatabase(context, "pc_builder")

private fun componentRepository(database: Database): Repository<Component, String> =
    RoomComponentRepository(database)
