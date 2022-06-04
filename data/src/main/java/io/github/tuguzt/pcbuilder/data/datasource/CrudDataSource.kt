package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.domain.model.Identifiable

interface CrudDataSource<I, T : Identifiable<I>> {
    suspend fun getAll(): List<T>

    suspend fun findById(id: I): T?

    suspend fun save(item: T)

    suspend fun delete(item: T)

    suspend fun clear()
}
