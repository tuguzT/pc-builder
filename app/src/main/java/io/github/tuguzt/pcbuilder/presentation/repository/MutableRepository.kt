package io.github.tuguzt.pcbuilder.presentation.repository

/**
 * Repository which can be **mutated**.
 *
 * @see Repository
 */
interface MutableRepository<T> : Repository<T> {
    suspend fun add(item: T)

    suspend fun update(item: T)

    suspend fun remove(item: T)

    suspend fun clear()
}
