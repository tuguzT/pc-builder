package io.github.tuguzt.pcbuilder.presentation.repository

import android.app.Application
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.mock.MockComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomRepository

/**
 * Object for access to all repository types used in the application.
 */
@Suppress("UNCHECKED_CAST")
object RepositoryAccess {
    @JvmStatic
    val localRepository: Repository<String, Component>
        get() = pLocalRepository
            ?: (MockComponentRepository as Repository<String, Component>)
                .also { pLocalRepository = it }

    @JvmStatic
    fun initRoom(application: Application) {
        val roomRepository = RoomRepository(application)
        pLocalRepository = roomRepository as Repository<String, Component>
    }

    private var pLocalRepository: Repository<String, Component>? = null
}
