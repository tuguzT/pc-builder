package io.github.tuguzt.pcbuilder.data.datasource.local.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Build Room database from [context] by [name] and optional [config].
 */
inline fun <reified T : RoomDatabase> buildDatabase(
    context: Context,
    name: String,
    config: RoomDatabase.Builder<T>.() -> Unit = {},
): T {
    val builder = Room.databaseBuilder(context, T::class.java, name)
    builder.config()
    return builder.build()
}
