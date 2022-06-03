package io.github.tuguzt.pcbuilder.repository.room.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import io.github.tuguzt.pcbuilder.repository.room.dto.component.cases.CaseMotherboardFormFactorCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface CaseMotherboardFormFactorCrossRefDao : IDao<CaseMotherboardFormFactorCrossRef> {
    @Query("SELECT * FROM CaseMotherboardFormFactorCrossRef")
    override fun getAll(): Flow<List<CaseMotherboardFormFactorCrossRef>>

    @Query(
        "SELECT * FROM CaseMotherboardFormFactorCrossRef" +
                " WHERE caseId = :caseId AND motherboardFormFactorId = :motherboardFormFactorId"
    )
    fun findByIds(
        caseId: String,
        motherboardFormFactorId: MotherboardFormFactor,
    ): Flow<CaseMotherboardFormFactorCrossRef>

    @Query("SELECT * FROM CaseMotherboardFormFactorCrossRef WHERE caseId = :caseId")
    suspend fun findByCaseIdNow(caseId: String): List<CaseMotherboardFormFactorCrossRef>

    @Query(
        "SELECT * FROM CaseMotherboardFormFactorCrossRef" +
                " WHERE caseId = :caseId AND motherboardFormFactorId = :motherboardFormFactorId"
    )
    suspend fun findByIdsNow(
        caseId: String,
        motherboardFormFactorId: MotherboardFormFactor,
    ): CaseMotherboardFormFactorCrossRef?

    @Query("DELETE FROM CaseMotherboardFormFactorCrossRef")
    override suspend fun clear()
}
