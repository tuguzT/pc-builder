package io.github.tuguzt.pcbuilder.repository.room.impl

import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.repository.Repository
import io.github.tuguzt.pcbuilder.repository.room.dao.ComponentDao
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ComponentDto
import io.github.tuguzt.pcbuilder.repository.room.toDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Room repository of [components][Component].
 *
 * @see Component
 */
class RoomComponentRepository(private val dao: ComponentDao) : Repository<String, Component> {
    override fun getAll(): Flow<List<ComponentDto>> = dao.getAll()

    override fun findById(id: String): Flow<ComponentDto> = dao.findById(id)

    override suspend fun save(item: Component): Unit = withContext(Dispatchers.IO) {
        if (dao.findByIdNow(item.id) == null) {
            dao.insert(item.toDto())
            return@withContext
        }
        dao.update(item.toDto())
    }

    override suspend fun delete(item: Component): Unit = withContext(Dispatchers.IO) {
        dao.delete(item.toDto())
    }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) { dao.clear() }

    fun findByName(name: String): Flow<List<ComponentDto>> = dao.findByName(name)
}
