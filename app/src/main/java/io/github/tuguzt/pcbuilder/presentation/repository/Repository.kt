package io.github.tuguzt.pcbuilder.presentation.repository

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.Identifiable

/**
 * Base interface for all repositories which contains data of type [T].
 */
interface Repository<I : Any, T : Identifiable<I>> {
    val allData: LiveData<out List<T>>

    fun findById(id: I): LiveData<out T>

    fun add(item: T)

    fun update(item: T)

    fun remove(item: T)

    fun clear()
}
