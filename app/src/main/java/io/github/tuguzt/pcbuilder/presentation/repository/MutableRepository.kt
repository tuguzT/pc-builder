package io.github.tuguzt.pcbuilder.presentation.repository

import io.github.tuguzt.pcbuilder.domain.model.Identifiable

/**
 * Repository which can be **mutated**.
 *
 * @see Repository
 */
interface MutableRepository<I : Comparable<I>, T : Identifiable<I>> : Repository<I, T> {
    suspend fun add(item: T)

    suspend fun update(item: T)

    suspend fun remove(item: T)

    suspend fun clear()
}
