package io.github.tuguzt.pcbuilder.data.datasource.local.room.dao

import androidx.room.*
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ManufacturerDto

@Dao
interface ManufacturerDao {
    @Query("SELECT * FROM manufacturer")
    suspend fun getAll(): List<ManufacturerDto>

    @Insert
    suspend fun insert(item: ManufacturerDto)

    @Update
    suspend fun update(item: ManufacturerDto)

    @Delete
    suspend fun delete(item: ManufacturerDto)

    @Query("SELECT * FROM manufacturer WHERE manufacturerId = :id")
    suspend fun findById(id: String): ManufacturerDto?

    @Query("DELETE FROM manufacturer")
    suspend fun clear()
}
