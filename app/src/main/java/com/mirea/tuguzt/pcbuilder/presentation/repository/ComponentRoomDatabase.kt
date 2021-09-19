package com.mirea.tuguzt.pcbuilder.presentation.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.presentation.repository.dao.ComponentDAO
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO

/**
 * Room database which contains all the components locally saved by user.
 *
 * @see Component
 */
@Database(entities = [ComponentDTO::class], version = 1, exportSchema = false)
internal abstract class ComponentRoomDatabase internal constructor(): RoomDatabase() {
    abstract val componentsDao: ComponentDAO

    companion object {
        @Volatile
        private var INSTANCE: ComponentRoomDatabase? = null

        /**
         * Get unique instance of the Room database.
         * @return unique instance of the Room database
         */
        @JvmStatic
        fun getInstance(context: Context): ComponentRoomDatabase {
            if (INSTANCE == null) {
                synchronized(ComponentRoomDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ComponentRoomDatabase::class.java,
                            "component_database",
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
