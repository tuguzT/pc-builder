package io.github.tuguzt.pcbuilder.repository.room.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ManufacturerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ManufacturerDao : IDao<String, ManufacturerEntity> {
    @Query("SELECT * FROM manufacturer")
    override fun getAll(): Flow<List<ManufacturerEntity>>

    @Query("SELECT * FROM manufacturer WHERE manufacturerId = :id")
    override fun findById(id: String): Flow<ManufacturerEntity>

    @Query("DELETE FROM manufacturer")
    override suspend fun clear()

    @Query("SELECT * FROM manufacturer WHERE manufacturerId = :id")
    suspend fun findByIdNow(id: String): ManufacturerEntity?
}
