package io.github.tuguzt.pcbuilder.presentation.repository.room

import android.app.Application
import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.presentation.repository.MutableRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Safe wrapper around Room database.
 *
 * @see RoomDatabase
 * @see ComponentDto
 */
internal class RoomRepository(application: Application) : MutableRepository<String, ComponentDto> {
    private val roomDatabase = RoomDatabase.getInstance(application)
    private val componentDao get() = roomDatabase.componentDao
    private val monitorDao get() = roomDatabase.monitorDao

    override val defaultDispatcher = Dispatchers.IO

    override val allData = componentDao.getAll()

    override fun findById(id: String): LiveData<out ComponentDto> = componentDao.findById(id)

    override suspend fun add(item: ComponentDto) = withContext(defaultDispatcher) {
        componentDao.insert(item)
    }

    override suspend fun update(item: ComponentDto) = withContext(defaultDispatcher) {
        componentDao.update(item)
    }

    override suspend fun remove(item: ComponentDto) = withContext(defaultDispatcher) {
        componentDao.delete(item)
    }

    override suspend fun clear() = withContext(defaultDispatcher) {
        componentDao.deleteAll()
    }
}
