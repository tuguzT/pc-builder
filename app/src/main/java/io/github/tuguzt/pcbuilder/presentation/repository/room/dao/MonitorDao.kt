package io.github.tuguzt.pcbuilder.presentation.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.tuguzt.pcbuilder.domain.model.monitor.Monitor
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.monitor.MonitorComponentDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.monitor.MonitorDto

/**
 * Data Access Object for [Monitor].
 *
 * @see Monitor
 */
@Dao
interface MonitorDao {
    @Insert
    suspend fun addMonitor(monitor: MonitorDto)

    @Delete
    suspend fun deleteMonitor(monitor: MonitorDto)

    @Transaction
    @Query("SELECT * FROM component")
    fun getMonitorComponent(): LiveData<List<MonitorComponentDto>>

    @Query("DELETE FROM monitor")
    suspend fun deleteAllMonitors()
}
