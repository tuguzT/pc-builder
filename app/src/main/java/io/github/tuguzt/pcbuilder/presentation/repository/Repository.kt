package io.github.tuguzt.pcbuilder.presentation.repository

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.Identifiable

/**
 * Base interface for all repositories which contains data of type [T].
 */
interface Repository<T : Identifiable<I>, I : Any> {
    val allData: LiveData<out List<T>>

    fun findById(id: I): LiveData<out T>

    suspend fun save(item: T)

    suspend fun delete(item: T)

    suspend fun clear()
}
