package io.github.tuguzt.pcbuilder.data.repository

import io.github.tuguzt.pcbuilder.domain.model.Identifiable

/**
 * Basic CRUD interface for repository which holds data of type [T].
 */
sealed interface CrudRepository<I, T : Identifiable<I>> {
    suspend fun getAll(): List<T>

    suspend fun findById(id: I): T?

    suspend fun save(item: T)

    suspend fun delete(item: T)

    suspend fun clear()
}
