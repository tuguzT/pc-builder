package com.mirea.tuguzt.pcbuilder.presentation.repository

import android.app.Application

/**
 * Object for access to all repository types used in the application.
 */
object RepositoryAccess {
    val roomRepository get() = privateRoomRepository

    @JvmStatic
    fun initRoomRepository(application: Application): ComponentRoomRepository {
        privateRoomRepository = ComponentRoomRepository(application)
        return roomRepository
    }

    private lateinit var privateRoomRepository: ComponentRoomRepository
}
