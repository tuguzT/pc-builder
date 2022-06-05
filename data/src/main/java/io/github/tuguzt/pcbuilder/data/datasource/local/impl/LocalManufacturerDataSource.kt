package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.ManufacturerDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.ManufacturerDao
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ManufacturerDto
import io.github.tuguzt.pcbuilder.data.datasource.local.room.toData
import io.github.tuguzt.pcbuilder.data.datasource.local.room.toEntity
import io.github.tuguzt.pcbuilder.data.toResult
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalManufacturerDataSource(private val dao: ManufacturerDao) : ManufacturerDataSource {
    override suspend fun getAll(): Result<List<ManufacturerData>, Error> =
        runCatching { dao.getAll().map(ManufacturerDto::toData) }.toResult()

    override suspend fun findById(id: NanoId): Result<ManufacturerData?, Error> =
        runCatching { dao.findById(id.toString())?.toData() }.toResult()

    override suspend fun save(item: ManufacturerData): Result<Unit, Error> =
        runCatching {
            withContext(Dispatchers.IO) {
                if (dao.findById(item.id.toString()) == null) {
                    dao.insert(item.toEntity())
                }
                dao.update(item.toEntity())
            }
        }.toResult()

    override suspend fun delete(item: ManufacturerData): Result<Unit, Error> =
        runCatching {
            withContext(Dispatchers.IO) { dao.delete(item.toEntity()) }
        }.toResult()

    override suspend fun clear(): Result<Unit, Error> =
        runCatching {
            withContext(Dispatchers.IO) { dao.clear() }
        }.toResult()
}
