package io.github.tuguzt.pcbuilder.presentation.repository.room

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.Monitor
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Room repository for [monitors][Monitor].
 *
 * @see Monitor
 */
internal class RoomMonitorRepository(private val roomDatabase: RoomDatabase) :
    Repository<String, Monitor> {

    private val monitorDao get() = roomDatabase.monitorDao

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override val allData: LiveData<out List<Monitor>> = monitorDao.getAll()

    override fun findById(id: String): LiveData<out Monitor> = monitorDao.findById(id)

    override fun add(item: Monitor) {
        TODO("Not yet implemented")
    }

    override fun update(item: Monitor) {
        TODO("Not yet implemented")
    }

    override fun remove(item: Monitor) {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }
}
