package io.github.tuguzt.pcbuilder.presentation.repository

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Base interface for all repositories which contains data of type [T].
 */
interface Repository<I, T : Identifiable<I>> {
    val defaultDispatcher: CoroutineDispatcher

    val allData: LiveData<out List<T>>

    fun findById(id: I) : LiveData<out T>

    suspend fun add(item: T)

    suspend fun update(item: T)

    suspend fun remove(item: T)

    suspend fun clear()
}
