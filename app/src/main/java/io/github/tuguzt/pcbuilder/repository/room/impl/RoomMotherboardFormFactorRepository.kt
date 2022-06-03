package io.github.tuguzt.pcbuilder.repository.room.impl

import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import io.github.tuguzt.pcbuilder.repository.Repository
import io.github.tuguzt.pcbuilder.repository.room.dao.MotherboardFormFactorDao
import io.github.tuguzt.pcbuilder.repository.room.dto.component.motherboard.MotherboardFormFactorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RoomMotherboardFormFactorRepository(
    private val dao: MotherboardFormFactorDao,
) : Repository<MotherboardFormFactor, MotherboardFormFactorEntity> {

    override fun getAll(): Flow<List<MotherboardFormFactorEntity>> = dao.getAll()

    override fun findById(id: MotherboardFormFactor): Flow<MotherboardFormFactorEntity> =
        dao.findById(id)

    suspend fun findByIdNow(id: MotherboardFormFactor): MotherboardFormFactorEntity? =
        withContext(Dispatchers.IO) { dao.findByIdNow(id) }

    override suspend fun save(item: MotherboardFormFactorEntity): Unit =
        withContext(Dispatchers.IO) {
            if (dao.findByIdNow(item.id) == null) {
                dao.insert(item)
            }
            dao.update(item)
        }

    override suspend fun delete(item: MotherboardFormFactorEntity): Unit =
        withContext(Dispatchers.IO) { dao.delete(item) }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) { dao.clear() }
}
