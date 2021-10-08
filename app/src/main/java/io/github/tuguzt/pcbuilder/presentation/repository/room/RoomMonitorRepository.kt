package io.github.tuguzt.pcbuilder.presentation.repository.room

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.Monitor
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import kotlinx.coroutines.Dispatchers

internal class RoomMonitorRepository(private val roomDatabase: RoomDatabase) :
    Repository<String, Monitor> {

    private val monitorDao get() = roomDatabase.monitorDao

    override val defaultDispatcher = Dispatchers.IO

    override val allData: LiveData<out List<Monitor>> = monitorDao.getAll()

    override fun findById(id: String): LiveData<out Monitor> = monitorDao.findById(id)

    override suspend fun add(item: Monitor) {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Monitor) {
        TODO("Not yet implemented")
    }

    override suspend fun remove(item: Monitor) {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }
}
