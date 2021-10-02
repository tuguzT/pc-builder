package io.github.tuguzt.pcbuilder.presentation.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Base interface for all repositories.
 * Cannot be mutated and acts as a view of [MutableRepository].
 *
 * @see MutableRepository
 */
interface Repository<out T> {
    val defaultDispatcher: CoroutineDispatcher

    val allData: LiveData<out List<T>>
}

fun <I : Comparable<I>, T : Identifiable<I>> Repository<T>.findById(
    id: String,
    owner: LifecycleOwner,
): LiveData<T> = MutableLiveData<T>().apply {
    allData.observe(owner) { components ->
        value = components.find { it.id == id }
    }
}
