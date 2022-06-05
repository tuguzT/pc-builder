package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.domain.model.Identifiable

interface CrudDataSource<I, T : Identifiable<I>> {
    suspend fun getAll(): Result<List<T>, Error>

    suspend fun findById(id: I): Result<T?, Error>

    suspend fun save(item: T): Result<Unit, Error>

    suspend fun delete(item: T): Result<Unit, Error>

    suspend fun clear(): Result<Unit, Error>
}
