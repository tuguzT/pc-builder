package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.ComponentDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.ComponentDao
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ComponentDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.toEntity
import io.github.tuguzt.pcbuilder.data.cast
import io.github.tuguzt.pcbuilder.data.toResult
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

    override suspend fun getAll(): Result<List<ComponentData>, Error> {
        return runCatching {
            componentDao.getAll().map {
                val manufacturer =
                    when (val result = manufacturerDataSource.findById(NanoId(it.manufacturerId))) {
                        is Result.Error -> return result.cast()
                        is Result.Success -> checkNotNull(result.data)
                    }
                makeComponentData(it, manufacturer)
            }
        }.toResult()
    }

    override suspend fun findById(id: NanoId): Result<ComponentData?, Error> {
        return runCatching {
            withContext(Dispatchers.IO) { componentDao.findById(id.toString()) }?.let {
                val manufacturer =
                    when (val result =
                        manufacturerDataSource.findById(NanoId(it.manufacturerId))) {
                        is Result.Error -> return result.cast()
                        is Result.Success -> checkNotNull(result.data)
                    }
                makeComponentData(it, manufacturer)
            }
        }.toResult()
    }

    override suspend fun save(item: ComponentData): Result<Unit, Error> =
        runCatching {
            withContext(Dispatchers.IO) {
                manufacturerDataSource.save(item.manufacturer)
                if (componentDao.findById(item.id.toString()) == null) {
                    componentDao.insert(item.toEntity())
                    return@withContext
                }
                componentDao.update(item.toEntity())
            }
        }.toResult()

    override suspend fun delete(item: ComponentData): Result<Unit, Error> =
        runCatching {
            withContext(Dispatchers.IO) { componentDao.delete(item.toEntity()) }
        }.toResult()

    override suspend fun clear(): Result<Unit, Error> =
        runCatching { withContext(Dispatchers.IO) { componentDao.clear() } }.toResult()

    override suspend fun findByName(name: String): Result<List<ComponentData>, Error> {
        return runCatching {
            componentDao.findByName(name).map {
                val manufacturer =
                    when (val result = manufacturerDataSource.findById(NanoId(it.manufacturerId))) {
                        is Result.Error -> return result.cast()
                        is Result.Success -> checkNotNull(result.data)
                    }
                makeComponentData(it, manufacturer)
            }
        }.toResult()
    }
}
