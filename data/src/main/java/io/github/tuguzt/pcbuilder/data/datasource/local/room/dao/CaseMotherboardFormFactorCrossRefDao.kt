package io.github.tuguzt.pcbuilder.data.datasource.local.room.dao

import androidx.room.*
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.CaseMotherboardFormFactorCrossRef
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor

@Dao
interface CaseMotherboardFormFactorCrossRefDao {
    @Query("SELECT * FROM CaseMotherboardFormFactorCrossRef")
    suspend fun getAll(): List<CaseMotherboardFormFactorCrossRef>

    @Insert
    suspend fun insert(item: CaseMotherboardFormFactorCrossRef)

    @Update
    suspend fun update(item: CaseMotherboardFormFactorCrossRef)

    @Delete
    suspend fun delete(item: CaseMotherboardFormFactorCrossRef)

    @Query("DELETE FROM CaseMotherboardFormFactorCrossRef")
    suspend fun clear()

    @Query(
        "SELECT * FROM CaseMotherboardFormFactorCrossRef" +
                " WHERE caseId = :caseId AND motherboardFormFactorId = :motherboardFormFactorId"
    )
    suspend fun findByIds(
        caseId: String,
        motherboardFormFactorId: MotherboardFormFactor,
    ): CaseMotherboardFormFactorCrossRef?

    @Query("SELECT * FROM CaseMotherboardFormFactorCrossRef WHERE caseId = :caseId")
    suspend fun findByCaseId(caseId: String): List<CaseMotherboardFormFactorCrossRef>
}
