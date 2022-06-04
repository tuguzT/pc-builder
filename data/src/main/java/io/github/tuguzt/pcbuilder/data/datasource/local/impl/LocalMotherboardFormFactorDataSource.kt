package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.data.datasource.MotherboardFormFactorDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.MotherboardFormFactorDao
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.motherboard.MotherboardFormFactorDto
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalMotherboardFormFactorDataSource(private val dao: MotherboardFormFactorDao) :
    MotherboardFormFactorDataSource {

    override suspend fun getAll(): List<MotherboardFormFactorDto> = dao.getAll()

    override suspend fun findById(id: MotherboardFormFactor): MotherboardFormFactorDto? =
        dao.findById(id)

    override suspend fun save(item: MotherboardFormFactorDto): Unit =
        withContext(Dispatchers.IO) {
            if (dao.findById(item.id) == null) {
                dao.insert(item)
            }
            dao.update(item)
        }

    override suspend fun delete(item: MotherboardFormFactorDto): Unit =
        withContext(Dispatchers.IO) { dao.delete(item) }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) { dao.clear() }
}
