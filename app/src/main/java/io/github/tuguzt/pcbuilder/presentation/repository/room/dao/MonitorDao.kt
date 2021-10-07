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
interface MonitorDao : IDao<MonitorDto> {
    @Transaction
    @Query("SELECT * FROM component")
    fun getAll(): LiveData<List<MonitorComponent>>

    @Query("DELETE FROM monitor")
    suspend fun deleteAll()
}
