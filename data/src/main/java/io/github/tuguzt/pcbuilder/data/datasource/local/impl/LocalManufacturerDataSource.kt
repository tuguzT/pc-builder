package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.data.datasource.ManufacturerDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.ManufacturerDao
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ManufacturerDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.toData
import io.github.tuguzt.pcbuilder.data.datasource.local.room.toEntity
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalManufacturerDataSource(private val dao: ManufacturerDao) : ManufacturerDataSource {
    override suspend fun getAll(): List<ManufacturerData> =
        dao.getAll().map(ManufacturerDto::toData)

    override suspend fun findById(id: NanoId): ManufacturerData? =
        dao.findById(id.toString())?.toData()

    override suspend fun save(item: ManufacturerData): Unit = withContext(Dispatchers.IO) {
        if (dao.findById(item.id.toString()) == null) {
            dao.insert(item.toEntity())
        }
        dao.update(item.toEntity())
    }

    override suspend fun delete(item: ManufacturerData): Unit =
        withContext(Dispatchers.IO) { dao.delete(item.toEntity()) }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) { dao.clear() }
}
