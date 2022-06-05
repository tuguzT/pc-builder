package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.MotherboardFormFactorDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.MotherboardFormFactorDao
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.motherboard.MotherboardFormFactorDto
import io.github.tuguzt.pcbuilder.data.toResult
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalMotherboardFormFactorDataSource(private val dao: MotherboardFormFactorDao) :
    MotherboardFormFactorDataSource {

    override suspend fun getAll(): Result<List<MotherboardFormFactorDto>, Error> =
        runCatching { dao.getAll() }.toResult()

    override suspend fun findById(id: MotherboardFormFactor): Result<MotherboardFormFactorDto?, Error> =
        runCatching { dao.findById(id) }.toResult()

    override suspend fun save(item: MotherboardFormFactorDto): Result<Unit, Error> =
        runCatching {
            withContext(Dispatchers.IO) {
                if (dao.findById(item.id) == null) {
                    dao.insert(item)
                }
                dao.update(item)
            }
        }.toResult()

    override suspend fun delete(item: MotherboardFormFactorDto): Result<Unit, Error> =
        runCatching { withContext(Dispatchers.IO) { dao.delete(item) } }.toResult()

    override suspend fun clear(): Result<Unit, Error> =
        runCatching { withContext(Dispatchers.IO) { dao.clear() } }.toResult()
}
