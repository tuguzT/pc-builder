package io.github.tuguzt.pcbuilder.repository.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import kotlinx.coroutines.flow.Flow

/**
 * Base DAO for the application.
 */
interface IDao<I : Any, T : Identifiable<I>> {
    /**
     * Retrieves all instances of [T].
     */
    fun getAll(): Flow<List<T>>

    /**
     * Finds one instance of [T] by [id].
     */
    fun findById(id: I): Flow<T>

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
