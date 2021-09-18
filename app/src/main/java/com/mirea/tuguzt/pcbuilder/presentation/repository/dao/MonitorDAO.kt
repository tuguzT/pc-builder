package com.mirea.tuguzt.pcbuilder.presentation.repository.dao

import androidx.room.*
import com.mirea.tuguzt.pcbuilder.domain.model.monitor.Monitor
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.monitor.MonitorComponentDTO
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.monitor.MonitorDTO

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
    fun getMonitorComponent(): List<MonitorComponentDTO>

    @Query("DELETE FROM monitor")
    fun deleteAllMonitors()
}
