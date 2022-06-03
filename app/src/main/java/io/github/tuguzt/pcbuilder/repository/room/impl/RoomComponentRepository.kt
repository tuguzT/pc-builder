package io.github.tuguzt.pcbuilder.repository.room.impl

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.repository.Repository
import io.github.tuguzt.pcbuilder.repository.room.dao.ComponentDao
import io.github.tuguzt.pcbuilder.repository.room.dao.ManufacturerDao
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ComponentEntity
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.repository.room.toData
import io.github.tuguzt.pcbuilder.repository.room.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Room repository of [components][Component].
 *
 * @see Component
 */
class RoomComponentRepository(
    private val componentDao: ComponentDao,
    private val manufacturerDao: ManufacturerDao,
) : Repository<NanoId, ComponentData> {

    override fun getAll(): Flow<List<ComponentData>> = componentDao.getAll()
        .map { componentEntities ->
            componentEntities
                .associateWith { manufacturerDao.findByIdNow(it.manufacturerId) }
                .map { makeComponentData(it.key, it.value!!) }
        }

    override fun findById(id: NanoId): Flow<ComponentData> = componentDao.findById("$id")
        .map {
            val manufacturer = manufacturerDao.findByIdNow(it.manufacturerId)!!
            makeComponentData(it, manufacturer)
        }

    override suspend fun save(item: ComponentData): Unit = withContext(Dispatchers.IO) {
        if (componentDao.findByIdNow(item.id.toString()) == null) {
            if (manufacturerDao.findByIdNow(item.manufacturer.id.toString()) == null) {
                manufacturerDao.insert(item.manufacturer.toEntity())
            }
            manufacturerDao.update(item.manufacturer.toEntity())
            componentDao.insert(item.toEntity())
            return@withContext
        }
        componentDao.update(item.toEntity())
    }

    override suspend fun delete(item: ComponentData): Unit = withContext(Dispatchers.IO) {
        componentDao.delete(item.toEntity())
    }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) { componentDao.clear() }

    fun findByName(name: String): Flow<List<ComponentData>> = componentDao.findByName(name)
        .map { componentEntities ->
            componentEntities
                .associateWith { manufacturerDao.findByIdNow(it.manufacturerId) }
                .map { makeComponentData(it.key, it.value!!) }
        }
}

private fun makeComponentData(
    component: ComponentEntity,
    manufacturer: ManufacturerEntity,
): ComponentData = ComponentData(
    id = NanoId(component.id),
    name = component.name,
    description = component.description,
    weight = Weight(component.weight),
    size = component.size,
    manufacturer = manufacturer.toData(),
)
