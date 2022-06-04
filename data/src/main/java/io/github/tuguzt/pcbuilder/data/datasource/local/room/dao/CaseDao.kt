package io.github.tuguzt.pcbuilder.data.datasource.local.room.dao

import androidx.room.*
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.cases.CaseDto

@Dao
interface CaseDao {
    @Query("SELECT * FROM cases")
    suspend fun getAll(): List<CaseDto>

    @Insert
    suspend fun insert(item: CaseDto)

    @Update
    suspend fun update(item: CaseDto)

    @Delete
    suspend fun delete(item: CaseDto)

    @Query("SELECT * FROM cases WHERE caseId = :id")
    suspend fun findById(id: String): CaseDto?

    @Query("DELETE FROM cases")
    suspend fun clear()
}
