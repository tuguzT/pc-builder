package io.github.tuguzt.pcbuilder.presentation.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.Monitor
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.monitor.MonitorComponents
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.monitor.MonitorDto

/**
 * Data Access Object for [Monitor].
 *
 * @see Monitor
 */
@Dao
interface MonitorDao : IDao<MonitorDto> {
    @Transaction
    @Query("SELECT * FROM component")
    fun getAll(): LiveData<List<MonitorComponents>>

    @Query("DELETE FROM monitor")
    suspend fun deleteAll()
}
