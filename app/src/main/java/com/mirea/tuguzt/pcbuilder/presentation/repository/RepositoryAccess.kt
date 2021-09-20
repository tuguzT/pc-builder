package com.mirea.tuguzt.pcbuilder.presentation.repository

import android.app.Application
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.presentation.repository.mock.MockComponentRepository
import com.mirea.tuguzt.pcbuilder.presentation.repository.room.RoomComponentRepository

/**
 * Object for access to all repository types used in the application.
 */
@Suppress("UNCHECKED_CAST")
object RepositoryAccess {
    @JvmStatic
    val localRepository: Repository<Component>
        get() {
            if (pLocalRepository == null) {
                pLocalRepository = MockComponentRepository as Repository<Component>
            }
            return pLocalRepository!!
        }

    @JvmStatic
    fun initRoom(application: Application): RoomComponentRepository {
        val roomRepository = RoomComponentRepository(application)
        pLocalRepository = roomRepository as Repository<Component>
        return roomRepository
    }

    private var pLocalRepository: Repository<Component>? = null
}
