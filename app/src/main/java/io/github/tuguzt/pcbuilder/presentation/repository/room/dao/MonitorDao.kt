package io.github.tuguzt.pcbuilder.presentation.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.Monitor
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.monitor.MonitorComponent
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.monitor.MonitorDto

/**
 * Data Access Object for [Monitor].
 *
 * @see Monitor
 */
@Dao
internal interface MonitorDao : IDao<MonitorDto> {
    @Transaction
    @Query("SELECT * FROM monitor WHERE component_id = :id")
    fun findById(id: String): LiveData<MonitorComponent>

    @Transaction
    @Query("SELECT * FROM monitor")
    fun getAll(): LiveData<List<MonitorComponent>>

    @Query("DELETE FROM monitor")
    suspend fun deleteAll()
}
