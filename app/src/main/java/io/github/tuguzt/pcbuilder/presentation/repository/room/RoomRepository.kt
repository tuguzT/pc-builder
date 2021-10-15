package io.github.tuguzt.pcbuilder.presentation.repository.room

import android.app.Application
import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.Repository

/**
 * Safe wrapper around Room database.
 *
 * @see RoomDatabase
 * @see Component
 */
internal class RoomRepository(application: Application) : Repository<String, Component> {
    private val roomDatabase = RoomDatabase.getInstance(application)
    private val componentRepository = RoomComponentRepository(roomDatabase)

    override val allData = componentRepository.allData

    override fun findById(id: String): LiveData<out Component> = componentRepository.findById(id)

    override fun add(item: Component) = componentRepository.add(item)

    override fun update(item: Component) = componentRepository.update(item)

    override fun remove(item: Component) = componentRepository.remove(item)

    override fun clear() = componentRepository.clear()
}
