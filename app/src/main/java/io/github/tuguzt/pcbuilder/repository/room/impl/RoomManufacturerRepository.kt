package io.github.tuguzt.pcbuilder.repository.room.impl

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.repository.Repository
import io.github.tuguzt.pcbuilder.repository.room.dao.ManufacturerDao
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.repository.room.toData
import io.github.tuguzt.pcbuilder.repository.room.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomManufacturerRepository(private val dao: ManufacturerDao) :
    Repository<NanoId, ManufacturerData> {

    override fun getAll(): Flow<List<ManufacturerData>> =
        dao.getAll().map { it.map(ManufacturerEntity::toData) }

    override fun findById(id: NanoId): Flow<ManufacturerData> =
        dao.findById(id.toString()).map { it.toData() }

    suspend fun findByIdNow(id: NanoId): ManufacturerData? =
        withContext(Dispatchers.IO) { dao.findByIdNow(id.toString())?.toData() }

    override suspend fun save(item: ManufacturerData): Unit = withContext(Dispatchers.IO) {
        if (dao.findByIdNow(item.id.toString()) == null) {
            dao.insert(item.toEntity())
        }
        dao.update(item.toEntity())
    }

    override suspend fun delete(item: ManufacturerData): Unit =
        withContext(Dispatchers.IO) { dao.delete(item.toEntity()) }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) { dao.clear() }
}
