package io.github.tuguzt.pcbuilder.repository.room.impl

import io.github.tuguzt.pcbuilder.repository.Repository
import io.github.tuguzt.pcbuilder.repository.room.dao.CaseMotherboardFormFactorCrossRefDao
import io.github.tuguzt.pcbuilder.repository.room.dto.component.cases.CaseMotherboardFormFactorCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RoomCaseMotherboardFormFactorCrossRefRepository(
    private val dao: CaseMotherboardFormFactorCrossRefDao,
) : Repository<CaseMotherboardFormFactorCrossRef, CaseMotherboardFormFactorCrossRef> {

    override fun getAll(): Flow<List<CaseMotherboardFormFactorCrossRef>> = dao.getAll()

    override fun findById(id: CaseMotherboardFormFactorCrossRef): Flow<CaseMotherboardFormFactorCrossRef> =
        dao.findByIds(id.caseId, id.motherboardFormFactorId)

    suspend fun findByCaseIdNow(caseId: String): List<CaseMotherboardFormFactorCrossRef> =
        withContext(Dispatchers.IO) { dao.findByCaseIdNow(caseId) }

    override suspend fun save(item: CaseMotherboardFormFactorCrossRef): Unit =
        withContext(Dispatchers.IO) {
            if (dao.findByIdsNow(item.caseId, item.motherboardFormFactorId) == null) {
                dao.insert(item)
            }
            dao.update(item)
        }

    override suspend fun delete(item: CaseMotherboardFormFactorCrossRef): Unit =
        withContext(Dispatchers.IO) { dao.delete(item) }

    override suspend fun clear(): Unit = withContext(Dispatchers.IO) { dao.clear() }
}
