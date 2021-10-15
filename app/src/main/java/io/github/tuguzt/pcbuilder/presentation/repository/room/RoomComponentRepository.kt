package io.github.tuguzt.pcbuilder.presentation.repository.room

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Room repository of [components][Component].
 *
 * @see Component
 */
internal class RoomComponentRepository(private val roomDatabase: RoomDatabase) :
    Repository<String, Component> {

    private val componentDao get() = roomDatabase.componentDao

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override val allData = componentDao.getAll()

    override fun findById(id: String): LiveData<out Component> = componentDao.findById(id)

    override fun add(item: Component) {
        val component = when (item) {
            is ComponentData -> ComponentDto(item)
            is ComponentDto -> item
            else -> throw IllegalStateException(
                "Data loss: item must be convertible to ${ComponentDto::class.qualifiedName}"
            )
        }
        coroutineScope.launch {
            componentDao.insert(component)
        }
    }

    override fun update(item: Component) {
        val component = when (item) {
            is ComponentData -> ComponentDto(item)
            is ComponentDto -> item
            else -> throw IllegalStateException(
                "Data loss: item must be convertible to ${ComponentDto::class.qualifiedName}"
            )
        }
        coroutineScope.launch {
            componentDao.update(component)
        }
    }

    override fun remove(item: Component) {
        val component = when (item) {
            is ComponentData -> ComponentDto(item)
            is ComponentDto -> item
            else -> throw IllegalStateException(
                "Data loss: item must be convertible to ${ComponentDto::class.qualifiedName}"
            )
        }
        coroutineScope.launch {
            componentDao.delete(component)
        }
    }

    override fun clear() {
        coroutineScope.launch {
            componentDao.deleteAll()
        }
    }
}
