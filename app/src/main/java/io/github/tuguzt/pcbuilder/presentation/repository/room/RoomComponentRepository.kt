package io.github.tuguzt.pcbuilder.presentation.repository.room

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class RoomComponentRepository(private val roomDatabase: RoomDatabase) :
    Repository<String, Component> {

    private val componentDao get() = roomDatabase.componentDao

    override val defaultDispatcher = Dispatchers.IO

    override val allData = componentDao.getAll()

    override fun findById(id: String): LiveData<out Component> = componentDao.findById(id)

    override suspend fun add(item: Component) {
        val component = when (item) {
            is ComponentData -> ComponentDto(item)
            is ComponentDto -> item
            else -> throw IllegalStateException(
                "Data loss: item must be convertible to ${ComponentDto::class.qualifiedName}"
            )
        }
        withContext(defaultDispatcher) {
            componentDao.insert(component)
        }
    }

    override suspend fun update(item: Component) {
        val component = when (item) {
            is ComponentData -> ComponentDto(item)
            is ComponentDto -> item
            else -> throw IllegalStateException(
                "Data loss: item must be convertible to ${ComponentDto::class.qualifiedName}"
            )
        }
        withContext(defaultDispatcher) {
            componentDao.update(component)
        }
    }

    override suspend fun remove(item: Component) {
        val component = when (item) {
            is ComponentData -> ComponentDto(item)
            is ComponentDto -> item
            else -> throw IllegalStateException(
                "Data loss: item must be convertible to ${ComponentDto::class.qualifiedName}"
            )
        }
        withContext(defaultDispatcher) {
            componentDao.delete(component)
        }
    }

    override suspend fun clear() = withContext(defaultDispatcher) {
        componentDao.deleteAll()
    }
}
