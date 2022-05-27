package io.github.tuguzt.pcbuilder.repository

import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import kotlinx.coroutines.flow.Flow

/**
 * Base interface for all repositories which contains data of type [T].
 */
interface Repository<I : Any, T : Identifiable<I>> {
    fun getAll(): Flow<List<T>>

    fun findById(id: I): Flow<T>

    suspend fun save(item: T)

    suspend fun delete(item: T)

    suspend fun clear()
}
