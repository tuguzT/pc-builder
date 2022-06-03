package io.github.tuguzt.pcbuilder.data.repository.room.dao

import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import kotlinx.coroutines.flow.Flow

interface IIdentifiableDao<I, T : Identifiable<I>> : IDao<T> {
    /**
     * Finds one instance of [T] by [id].
     */
    fun findById(id: I): Flow<T>

    /**
     * Finds one instance of [T] by [id].
     */
    suspend fun findByIdNow(id: I): T?
}
