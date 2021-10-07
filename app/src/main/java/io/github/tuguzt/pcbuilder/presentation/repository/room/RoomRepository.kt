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
class RoomRepository internal constructor(application: Application) :
    MutableRepository<String, ComponentDto> {

    private val roomDatabase = RoomDatabase.getInstance(application)
    private val componentsDao get() = roomDatabase.componentsDao
    private val monitorDao get() = roomDatabase.monitorDao

    override val defaultDispatcher = Dispatchers.IO

    override val allData = componentsDao.getAll()

    override fun findById(id: String): LiveData<out ComponentDto> = componentsDao.findById(id)

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
