package io.github.tuguzt.pcbuilder.data.repository.room.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.tuguzt.pcbuilder.data.repository.room.dto.component.cases.CaseMotherboardFormFactorCrossRef
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
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
