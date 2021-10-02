package io.github.tuguzt.pcbuilder.presentation.repository.room

import android.app.Application
import io.github.tuguzt.pcbuilder.presentation.repository.MutableRepository
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
    MutableRepository<String, ComponentDto> {

    private val roomDatabase = RoomComponentDatabase.getInstance(application)
    private val componentsDao get() = roomDatabase.componentsDao

    override val defaultDispatcher = Dispatchers.IO

    override val allData = componentsDao.getAll()

    override suspend fun add(item: ComponentDto) = withContext(defaultDispatcher) {
        componentsDao.insert(item)
    }

    override suspend fun update(item: ComponentDto) = withContext(defaultDispatcher) {
        componentsDao.update(item)
    }

    override suspend fun remove(item: ComponentDto) = withContext(defaultDispatcher) {
        componentsDao.delete(item)
    }

    override suspend fun clear() = withContext(defaultDispatcher) {
        componentsDao.deleteAll()
    }
}
