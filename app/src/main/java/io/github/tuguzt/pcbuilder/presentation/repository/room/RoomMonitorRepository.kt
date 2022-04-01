package io.github.tuguzt.pcbuilder.presentation.repository.room

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.Monitor
import io.github.tuguzt.pcbuilder.presentation.repository.Repository

/**
 * Room repository for [monitors][Monitor].
 *
 * @see Monitor
 */
class RoomMonitorRepository(private val database: Database) : Repository<Monitor, String> {
    private val monitorDao get() = database.monitorDao

    override val allData: LiveData<out List<Monitor>> = monitorDao.getAll()

    override fun findById(id: String): LiveData<out Monitor> = monitorDao.findById(id)

    override suspend fun save(item: Monitor) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Monitor) {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }
}
