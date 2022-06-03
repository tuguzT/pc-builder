package io.github.tuguzt.pcbuilder.data.repository.room.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.tuguzt.pcbuilder.data.repository.room.dto.component.motherboard.MotherboardFormFactorEntity
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import kotlinx.coroutines.flow.Flow

@Dao
interface MotherboardFormFactorDao :
    IIdentifiableDao<MotherboardFormFactor, MotherboardFormFactorEntity> {

    @Query("SELECT * FROM motherboard_form_factor")
    override fun getAll(): Flow<List<MotherboardFormFactorEntity>>

    @Query("SELECT * FROM motherboard_form_factor WHERE motherboardFormFactorId = :id")
    override fun findById(id: MotherboardFormFactor): Flow<MotherboardFormFactorEntity>

    @Query("DELETE FROM motherboard_form_factor")
    override suspend fun clear()

    @Query("SELECT * FROM motherboard_form_factor WHERE motherboardFormFactorId = :id")
    override suspend fun findByIdNow(id: MotherboardFormFactor): MotherboardFormFactorEntity?
}
