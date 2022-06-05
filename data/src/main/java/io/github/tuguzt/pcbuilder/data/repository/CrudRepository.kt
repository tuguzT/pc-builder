package io.github.tuguzt.pcbuilder.data.repository

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.domain.model.Identifiable

/**
 * Basic CRUD interface for repository which holds data of type [T].
 */
sealed interface CrudRepository<I, T : Identifiable<I>> {
    suspend fun getAll(): Result<List<T>, Error>

    suspend fun findById(id: I): Result<T?, Error>

    suspend fun save(item: T): Result<Unit, Error>

    suspend fun delete(item: T): Result<Unit, Error>

    suspend fun clear(): Result<Unit, Error>
}
