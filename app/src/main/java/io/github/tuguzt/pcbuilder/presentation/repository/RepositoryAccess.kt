package io.github.tuguzt.pcbuilder.presentation.repository

import android.app.Application
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.mock.MockComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomComponentRepository

/**
 * Object for access to all repository types used in the application.
 */
@Suppress("UNCHECKED_CAST")
object RepositoryAccess {
    @JvmStatic
    val localRepository: MutableRepository<String, Component>
        get() {
            if (pLocalRepository == null) {
                pLocalRepository = MockComponentRepository as MutableRepository<String, Component>
            }
            return pLocalRepository!!
        }

    @JvmStatic
    fun initRoom(application: Application): RoomComponentRepository {
        val roomRepository = RoomComponentRepository(application)
        pLocalRepository = roomRepository as MutableRepository<String, Component>
        return roomRepository
    }

    private var pLocalRepository: MutableRepository<String, Component>? = null
}
