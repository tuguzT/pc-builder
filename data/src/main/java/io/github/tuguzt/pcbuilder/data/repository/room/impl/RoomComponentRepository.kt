package io.github.tuguzt.pcbuilder.data.repository.room.impl

import io.github.tuguzt.pcbuilder.data.repository.Repository
import io.github.tuguzt.pcbuilder.data.repository.room.dao.ComponentDao
import io.github.tuguzt.pcbuilder.data.repository.room.dto.component.ComponentEntity
import io.github.tuguzt.pcbuilder.data.repository.room.toEntity
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
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
    private val manufacturerRepository: RoomManufacturerRepository,
) : Repository<NanoId, ComponentData> {

    companion object {
        @JvmStatic
        private fun makeComponentData(
            component: ComponentEntity,
            manufacturer: ManufacturerData,
        ): ComponentData = ComponentData(
            id = NanoId(component.id),
            name = component.name,
            description = component.description,
            weight = Weight(component.weight),
            size = component.size,
            manufacturer = manufacturer,
        )
    }

    override fun getAll(): Flow<List<ComponentData>> = componentDao.getAll()
        .map { componentEntities ->
            componentEntities
                .associateWith { manufacturerRepository.findByIdNow(NanoId(it.manufacturerId)) }
                .map { makeComponentData(it.key, it.value!!) }
        }

    override fun findById(id: NanoId): Flow<ComponentData> = componentDao.findById("$id")
        .map {
            val manufacturer = manufacturerRepository.findByIdNow(NanoId(it.manufacturerId))!!
            makeComponentData(it, manufacturer)
        }

    suspend fun findByIdNow(id: NanoId): ComponentData? = withContext(Dispatchers.IO) {
        componentDao.findByIdNow(id.toString())?.let {
            val manufacturer = manufacturerRepository.findByIdNow(NanoId(it.manufacturerId))!!
            makeComponentData(it, manufacturer)
        }
    }

    override suspend fun save(item: ComponentData): Unit = withContext(Dispatchers.IO) {
        manufacturerRepository.save(item.manufacturer)
        if (componentDao.findByIdNow(item.id.toString()) == null) {
            componentDao.insert(item.toEntity())
            return@withContext
        }
        componentDao.update(item.toEntity())
    }

    override suspend fun delete(item: ComponentData): Unit =
        withContext(Dispatchers.IO) { componentDao.delete(item.toEntity()) }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) { componentDao.clear() }

    fun findByName(name: String): Flow<List<ComponentData>> = componentDao.findByName(name)
        .map { componentEntities ->
            componentEntities
                .associateWith { manufacturerRepository.findByIdNow(NanoId(it.manufacturerId)) }
                .map { makeComponentData(it.key, it.value!!) }
        }
}
