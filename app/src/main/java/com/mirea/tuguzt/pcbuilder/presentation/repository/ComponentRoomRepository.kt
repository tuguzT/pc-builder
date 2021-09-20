package com.mirea.tuguzt.pcbuilder.presentation.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO

/**
 * Safe wrapper around Room database.
 *
 * @see ComponentRoomDatabase
 * @see ComponentDTO
 */
class ComponentRoomRepository internal constructor(application: Application) :
    Repository<ComponentDTO> {

    private val roomDatabase = ComponentRoomDatabase.getInstance(application)
    private val componentsDao get() = roomDatabase.componentsDao

    override fun getAllComponents(): LiveData<List<ComponentDTO>> {
        return componentsDao.getAllComponents()
    }

    override suspend fun addComponent(component: ComponentDTO) {
        componentsDao.addComponent(component)
    }

    override suspend fun deleteComponent(component: ComponentDTO) {
        componentsDao.deleteComponent(component)
    }

    override suspend fun deleteAllComponents() {
        componentsDao.deleteAllComponents()
    }
}
