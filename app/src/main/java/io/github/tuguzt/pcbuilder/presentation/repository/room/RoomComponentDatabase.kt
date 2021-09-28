package io.github.tuguzt.pcbuilder.presentation.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.room.dao.ComponentDao
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto

/**
 * Room database which contains all the components locally saved by user.
 *
 * @see Component
 */
@Database(entities = [ComponentDto::class], version = 1, exportSchema = false)
internal abstract class RoomComponentDatabase internal constructor() : RoomDatabase() {
    abstract val componentsDao: ComponentDao

    companion object {
        private const val DATABASE_NAME = "component_database"

        @Volatile
        private var INSTANCE: RoomComponentDatabase? = null

        /**
         * Get unique instance of the Room database.
         * @return unique instance of the Room database
         */
        @JvmStatic
        fun getInstance(context: Context): RoomComponentDatabase {
            if (INSTANCE == null) {
                synchronized(RoomComponentDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RoomComponentDatabase::class.java,
                            DATABASE_NAME,
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
