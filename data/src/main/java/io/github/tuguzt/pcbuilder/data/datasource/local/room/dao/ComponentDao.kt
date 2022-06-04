package io.github.tuguzt.pcbuilder.data.datasource.local.room.dao

import androidx.room.*
import io.github.tuguzt.pcbuilder.data.datasource.local.room.dto.component.ComponentDto
import io.github.tuguzt.pcbuilder.domain.model.component.Component

/**
 * Data Access Object for [Component].
 */
@Dao
interface ComponentDao {
    @Query("SELECT * FROM component")
    suspend fun getAll(): List<ComponentDto>

    @Insert
    suspend fun insert(item: ComponentDto)

    @Update
    suspend fun update(item: ComponentDto)

    @Delete
    suspend fun delete(item: ComponentDto)

    @Query("SELECT * FROM component WHERE componentId = :id")
    suspend fun findById(id: String): ComponentDto?

    @Query("DELETE FROM component")
    suspend fun clear()

    @Query("SELECT * FROM component WHERE name LIKE :name")
    suspend fun findByName(name: String): List<ComponentDto>
}
