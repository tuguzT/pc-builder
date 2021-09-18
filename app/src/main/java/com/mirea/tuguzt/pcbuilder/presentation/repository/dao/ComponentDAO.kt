package com.mirea.tuguzt.pcbuilder.presentation.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO

/**
 * Data Access Object for [Component].
 *
 * @see Component
 */
@Dao
interface ComponentDAO {
    @Query("SELECT * FROM component WHERE name LIKE :name")
    fun findByName(name: String): List<ComponentDTO>

    @Insert
    fun addComponent(component: ComponentDTO)

    @Delete
    fun deleteComponent(component: ComponentDTO)

    @Query("SELECT * FROM component")
    fun getAllComponents(): List<ComponentDTO>

    @Query("DELETE FROM component")
    fun deleteAllComponents()
}
