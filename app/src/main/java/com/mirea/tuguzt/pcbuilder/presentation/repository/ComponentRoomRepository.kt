package com.mirea.tuguzt.pcbuilder.presentation.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO

/**
 * Safe wrapper around Room database.
 *
 * @see ComponentRoomDatabase
 */
class ComponentRoomRepository internal constructor(application: Application) : Repository {
    private val roomDatabase = ComponentRoomDatabase.getInstance(application)
    private val componentDao = roomDatabase.componentsDao

    override fun getAllComponents(): LiveData<List<ComponentDTO>> {
        return componentDao.getAllComponents()
    }

    override suspend fun addComponent(component: ComponentDTO) {
        componentDao.addComponent(component)
    }

    override suspend fun deleteComponent(component: ComponentDTO) {
        componentDao.deleteComponent(component)
    }

    override suspend fun deleteAllComponents() {
        componentDao.deleteAllComponents()
    }
}
