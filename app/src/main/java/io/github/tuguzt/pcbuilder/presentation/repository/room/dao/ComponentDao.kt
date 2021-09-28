package io.github.tuguzt.pcbuilder.presentation.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.tuguzt.pcbuilder.domain.model.Component
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto

/**
 * Data Access Object for [Component].
 *
 * @see Component
 */
@Dao
interface ComponentDao {
    @Query("SELECT * FROM component WHERE name LIKE :name")
    fun findByName(name: String): LiveData<List<ComponentDto>>

    @Insert
    suspend fun insert(component: ComponentDto)

    @Delete
    suspend fun delete(component: ComponentDto)

    @Query("SELECT * FROM component")
    fun getAll(): LiveData<List<ComponentDto>>

    @Query("DELETE FROM component")
    suspend fun deleteAll()
}
