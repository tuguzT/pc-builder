package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.CaseMotherboardFormFactorCrossRefDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.CaseMotherboardFormFactorCrossRefDao
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.CaseMotherboardFormFactorCrossRef
import io.github.tuguzt.pcbuilder.data.toResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalCaseMotherboardFormFactorCrossRefDataSource(
    private val dao: CaseMotherboardFormFactorCrossRefDao,
) : CaseMotherboardFormFactorCrossRefDataSource {

    override suspend fun getAll(): Result<List<CaseMotherboardFormFactorCrossRef>, Error> =
        runCatching { dao.getAll() }.toResult()

    override suspend fun findById(id: CaseMotherboardFormFactorCrossRef): Result<CaseMotherboardFormFactorCrossRef?, Error> =
        runCatching { dao.findByIds(id.caseId, id.motherboardFormFactorId) }.toResult()

    override suspend fun findByCaseId(caseId: String): Result<List<CaseMotherboardFormFactorCrossRef>, Error> =
        runCatching { withContext(Dispatchers.IO) { dao.findByCaseId(caseId) } }.toResult()

    override suspend fun save(item: CaseMotherboardFormFactorCrossRef): Result<Unit, Error> =
        runCatching {
            withContext(Dispatchers.IO) {
                if (dao.findByIds(item.caseId, item.motherboardFormFactorId) == null) {
                    dao.insert(item)
                }
                dao.update(item)
            }
        }.toResult()

    override suspend fun delete(item: CaseMotherboardFormFactorCrossRef): Result<Unit, Error> =
        runCatching { withContext(Dispatchers.IO) { dao.delete(item) } }.toResult()

    override suspend fun clear(): Result<Unit, Error> =
        runCatching { withContext(Dispatchers.IO) { dao.clear() } }.toResult()
}
