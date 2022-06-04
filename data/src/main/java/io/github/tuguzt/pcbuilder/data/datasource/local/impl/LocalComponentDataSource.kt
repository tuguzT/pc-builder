package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.data.datasource.ComponentDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.ComponentDao
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ComponentDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.toEntity
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Room repository of [components][Component].
 */
class LocalComponentDataSource(
    private val componentDao: ComponentDao,
    private val manufacturerDataSource: LocalManufacturerDataSource,
) : ComponentDataSource {

    companion object {
        @JvmStatic
        private fun makeComponentData(
            component: ComponentDto,
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

    override suspend fun getAll(): List<ComponentData> = componentDao.getAll().map {
        val manufacturer = manufacturerDataSource.findById(NanoId(it.manufacturerId))!!
        makeComponentData(it, manufacturer)
    }

    override suspend fun findById(id: NanoId): ComponentData? = withContext(Dispatchers.IO) {
        componentDao.findById(id.toString())?.let {
            val manufacturer = manufacturerDataSource.findById(NanoId(it.manufacturerId))!!
            makeComponentData(it, manufacturer)
        }
    }

    override suspend fun save(item: ComponentData): Unit = withContext(Dispatchers.IO) {
        manufacturerDataSource.save(item.manufacturer)
        if (componentDao.findById(item.id.toString()) == null) {
            componentDao.insert(item.toEntity())
            return@withContext
        }
        componentDao.update(item.toEntity())
    }

    override suspend fun delete(item: ComponentData): Unit =
        withContext(Dispatchers.IO) { componentDao.delete(item.toEntity()) }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) { componentDao.clear() }

    override suspend fun findByName(name: String): List<ComponentData> =
        componentDao.findByName(name).map {
            val manufacturer = manufacturerDataSource.findById(NanoId(it.manufacturerId))!!
            makeComponentData(it, manufacturer)
        }
}
