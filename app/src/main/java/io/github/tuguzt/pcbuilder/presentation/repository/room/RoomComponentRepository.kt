package io.github.tuguzt.pcbuilder.presentation.repository.room

import android.app.Application
import io.github.tuguzt.pcbuilder.domain.model.Component
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Safe wrapper around Room database.
 *
 * @see RoomComponentDatabase
 * @see ComponentDto
 */
class RoomComponentRepository internal constructor(application: Application) :
    Repository<ComponentDto> {

    private val roomDatabase = RoomComponentDatabase.getInstance(application)
    private val componentsDao get() = roomDatabase.componentsDao

    override val defaultDispatcher = Dispatchers.IO

    override val allComponents = componentsDao.getAllComponents()

    override suspend fun add(component: Component) {
        @Suppress("NAME_SHADOWING")
        val component = when (component) {
            is ComponentDto -> component
            else -> ComponentDto(component)
        }
        withContext(defaultDispatcher) {
            componentsDao.addComponent(component)
        }
    }

    override suspend fun remove(component: ComponentDto) = withContext(defaultDispatcher) {
        componentsDao.deleteComponent(component)
    }

    override suspend fun clear() = withContext(defaultDispatcher) {
        componentsDao.deleteAllComponents()
    }
}
