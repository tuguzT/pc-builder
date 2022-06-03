package io.github.tuguzt.pcbuilder.repository.room.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.repository.room.dto.component.ComponentEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for [Component].
 *
 * @see Component
 */
@Dao
interface ComponentDao : IDao<String, ComponentEntity> {
    @Query("SELECT * FROM component")
    override fun getAll(): Flow<List<ComponentEntity>>

    @Query("SELECT * FROM component WHERE componentId = :id")
    override fun findById(id: String): Flow<ComponentEntity>

    @Query("DELETE FROM component")
    override suspend fun clear()

    @Query("SELECT * FROM component WHERE componentId = :id")
    suspend fun findByIdNow(id: String): ComponentEntity?

    @Query("SELECT * FROM component WHERE name LIKE :name")
    fun findByName(name: String): Flow<List<ComponentEntity>>
}
