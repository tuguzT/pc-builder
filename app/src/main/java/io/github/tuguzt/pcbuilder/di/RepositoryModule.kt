package io.github.tuguzt.pcbuilder.di

import android.content.Context
import androidx.room.Room
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Repository module of the Koin DI.
 */
val repositoryModule = module {
    single { database(androidContext()) }
    single { componentRepository(get()) }
}

private fun database(context: Context) =
    Room.databaseBuilder(
        context.applicationContext,
        RoomDatabase::class.java,
        "pc_builder",
    ).build()

private fun componentRepository(roomDatabase: RoomDatabase): Repository<String, Component> =
    RoomComponentRepository(roomDatabase)
