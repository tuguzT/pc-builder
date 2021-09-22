package io.github.tuguzt.pcbuilder.presentation.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.tuguzt.pcbuilder.domain.model.monitor.Monitor
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.monitor.MonitorComponentDTO
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.monitor.MonitorDTO

/**
 * Data Access Object for [Monitor].
 *
 * @see Monitor
 */
@Dao
interface MonitorDAO {
    @Insert
    fun addMonitor(monitor: MonitorDTO)

    @Delete
    fun deleteMonitor(monitor: MonitorDTO)

    @Transaction
    @Query("SELECT * FROM component")
    fun getMonitorComponent(): LiveData<List<MonitorComponentDTO>>

    @Query("DELETE FROM monitor")
    fun deleteAllMonitors()
}
