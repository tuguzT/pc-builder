package io.github.tuguzt.pcbuilder.data.repository.room.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.tuguzt.pcbuilder.data.repository.room.dto.component.cases.CaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CaseDao : IIdentifiableDao<String, CaseEntity> {
    @Query("SELECT * FROM cases")
    override fun getAll(): Flow<List<CaseEntity>>

    @Query("SELECT * FROM cases WHERE caseId = :id")
    override fun findById(id: String): Flow<CaseEntity>

    @Query("SELECT * FROM cases WHERE caseId = :id")
    override suspend fun findByIdNow(id: String): CaseEntity?

    @Query("DELETE FROM cases")
    override suspend fun clear()
}
