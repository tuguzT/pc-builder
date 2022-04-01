package io.github.tuguzt.pcbuilder.presentation.repository.room

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Room repository of [components][Component].
 *
 * @see Component
 */
class RoomComponentRepository(private val database: Database) : Repository<Component, String> {
    private val componentDao get() = database.componentDao

    override val allData = componentDao.getAll()

    override fun findById(id: String): LiveData<out Component> = componentDao.findById(id)

    override suspend fun save(item: Component) = withContext(Dispatchers.IO) {
        if (componentDao.findByIdSuspend(item.id) == null) {
            componentDao.insert(item.toDto())
            return@withContext
        }
        componentDao.update(item.toDto())
    }

    override suspend fun delete(item: Component) =
        withContext(Dispatchers.IO) { componentDao.delete(item.toDto()) }

    override suspend fun clear() = withContext(Dispatchers.IO) { componentDao.deleteAll() }
}
