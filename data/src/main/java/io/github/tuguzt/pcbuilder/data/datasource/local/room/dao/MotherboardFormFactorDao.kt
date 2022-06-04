package io.github.tuguzt.pcbuilder.data.datasource.local.room.dao

import androidx.room.*
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.motherboard.MotherboardFormFactorDto
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor

@Dao
interface MotherboardFormFactorDao {
    @Query("SELECT * FROM motherboard_form_factor")
    suspend fun getAll(): List<MotherboardFormFactorDto>

    @Insert
    suspend fun insert(item: MotherboardFormFactorDto)

    @Update
    suspend fun update(item: MotherboardFormFactorDto)

    @Delete
    suspend fun delete(item: MotherboardFormFactorDto)

    @Query("SELECT * FROM motherboard_form_factor WHERE motherboardFormFactorId = :id")
    suspend fun findById(id: MotherboardFormFactor): MotherboardFormFactorDto?

    @Query("DELETE FROM motherboard_form_factor")
    suspend fun clear()
}
