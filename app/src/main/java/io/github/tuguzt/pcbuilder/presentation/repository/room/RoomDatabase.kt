package io.github.tuguzt.pcbuilder.presentation.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.room.dao.ComponentDao
import io.github.tuguzt.pcbuilder.presentation.repository.room.dao.MonitorDao
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.monitor.MonitorDto

private typealias BaseRoomDatabase = androidx.room.RoomDatabase

/**
 * Room database which contains all the components locally saved by user.
 *
 * @see Component
 */
@Database(
    entities = [ComponentDto::class, MonitorDto::class],
    version = 1,
    exportSchema = false,
)
internal abstract class RoomDatabase internal constructor() : BaseRoomDatabase() {
    abstract val componentsDao: ComponentDao
    abstract val monitorDao: MonitorDao

    companion object {
        private const val DATABASE_NAME = "pc_builder"

        @Volatile
        private var INSTANCE: RoomDatabase? = null

        /**
         * Get unique instance of the Room database.
         * @return unique instance of the Room database
         */
        @JvmStatic
        fun getInstance(context: Context): RoomDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                RoomDatabase::class.java,
                DATABASE_NAME,
            ).build().also { INSTANCE = it }
        }
    }
}
