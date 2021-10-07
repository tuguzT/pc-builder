package io.github.tuguzt.pcbuilder.presentation.repository

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Base interface for all repositories which contains data of type [T].
 * Cannot be mutated and acts as a view of [MutableRepository].
 *
 * @see MutableRepository
 */
interface Repository<I, out T : Identifiable<I>> {
    val defaultDispatcher: CoroutineDispatcher

    val allData: LiveData<out List<T>>

    fun findById(id: I) : LiveData<out T>
}
