package com.mirea.tuguzt.pcbuilder.presentation.repository.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.mirea.tuguzt.pcbuilder.presentation.repository.Repository
import com.mirea.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Safe wrapper around Room database.
 *
 * @see RoomComponentDatabase
 * @see ComponentDTO
 */
class RoomComponentRepository internal constructor(application: Application) :
    Repository<ComponentDTO> {

    private val roomDatabase = RoomComponentDatabase.getInstance(application)
    private val componentsDao get() = roomDatabase.componentsDao

    override val defaultDispatcher = Dispatchers.IO

    override fun getAllComponents(): LiveData<List<ComponentDTO>> = componentsDao.getAllComponents()

    override suspend fun addComponent(component: ComponentDTO) = withContext(defaultDispatcher) {
        componentsDao.addComponent(component)
    }

    override suspend fun deleteComponent(component: ComponentDTO) = withContext(defaultDispatcher) {
        componentsDao.deleteComponent(component)
    }

    override suspend fun deleteAllComponents() = withContext(defaultDispatcher) {
        componentsDao.deleteAllComponents()
    }
}
