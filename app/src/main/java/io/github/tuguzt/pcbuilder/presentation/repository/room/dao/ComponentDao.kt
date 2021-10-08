package io.github.tuguzt.pcbuilder.presentation.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto

/**
 * Data Access Object for [Component].
 *
 * @see Component
 */
@Dao
internal interface ComponentDao : IDao<ComponentDto> {
    @Query("SELECT * FROM component WHERE id = :id")
    fun findById(id: String): LiveData<ComponentDto>

    @Query("SELECT * FROM component WHERE name LIKE :name")
    fun findByName(name: String): LiveData<List<ComponentDto>>

    @Query("SELECT * FROM component")
    fun getAll(): LiveData<List<ComponentDto>>

    @Query("DELETE FROM component")
    suspend fun deleteAll()
}
