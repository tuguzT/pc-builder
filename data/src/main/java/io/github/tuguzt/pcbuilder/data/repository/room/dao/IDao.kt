package io.github.tuguzt.pcbuilder.data.repository.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Base DAO for the application.
 */
interface IDao<T> {
    /**
     * Retrieves all instances of [T].
     */
    fun getAll(): Flow<List<T>>

    /**
     * Inserts one instance of [T] into the table.
     */
    @Insert
    suspend fun insert(item: T)

    /**
     * Updates one instance of [T] existing in the table.
     */
    @Update
    suspend fun update(item: T)

    /**
     * Deletes one instance of [T] from the table.
     */
    @Delete
    suspend fun delete(item: T)

    /**
     * Clears the table of instances of [T].
     */
    suspend fun clear()
}
