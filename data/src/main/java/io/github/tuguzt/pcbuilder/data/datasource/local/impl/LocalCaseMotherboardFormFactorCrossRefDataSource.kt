package io.github.tuguzt.pcbuilder.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.data.datasource.CaseMotherboardFormFactorCrossRefDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dao.CaseMotherboardFormFactorCrossRefDao
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.CaseMotherboardFormFactorCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalCaseMotherboardFormFactorCrossRefDataSource(
    private val dao: CaseMotherboardFormFactorCrossRefDao,
) : CaseMotherboardFormFactorCrossRefDataSource {

    override suspend fun getAll(): List<CaseMotherboardFormFactorCrossRef> = dao.getAll()

    override suspend fun findById(id: CaseMotherboardFormFactorCrossRef): CaseMotherboardFormFactorCrossRef? =
        dao.findByIds(id.caseId, id.motherboardFormFactorId)

    override suspend fun findByCaseId(caseId: String): List<CaseMotherboardFormFactorCrossRef> =
        withContext(Dispatchers.IO) { dao.findByCaseId(caseId) }

    override suspend fun save(item: CaseMotherboardFormFactorCrossRef): Unit =
        withContext(Dispatchers.IO) {
            if (dao.findByIds(item.caseId, item.motherboardFormFactorId) == null) {
                dao.insert(item)
            }
            dao.update(item)
        }

    override suspend fun delete(item: CaseMotherboardFormFactorCrossRef): Unit =
        withContext(Dispatchers.IO) { dao.delete(item) }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) { dao.clear() }
}
