package io.github.tuguzt.pcbuilder.presentation.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.tuguzt.pcbuilder.domain.model.Component
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDTO

/**
 * Data Access Object for [Component].
 *
 * @see Component
 */
@Dao
interface ComponentDAO {
    @Query("SELECT * FROM component WHERE name LIKE :name")
    fun findByName(name: String): LiveData<List<ComponentDTO>>

    @Insert
    fun addComponent(component: ComponentDTO)

    @Delete
    fun deleteComponent(component: ComponentDTO)

    @Query("SELECT * FROM component")
    fun getAllComponents(): LiveData<List<ComponentDTO>>

    @Query("DELETE FROM component")
    fun deleteAllComponents()
}
