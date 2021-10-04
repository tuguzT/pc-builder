package io.github.tuguzt.pcbuilder.presentation.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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

fun <I, T : Identifiable<out I>> Repository<T>.findById(
    id: String,
    owner: LifecycleOwner? = null,
): LiveData<T> = MutableLiveData<T>().apply {
    val observer = Observer<List<T>> { components ->
        value = components.find { it.id == id }
    }
    if (owner == null) {
        allData.observeForever(observer)
        return@apply
    }
    allData.observe(owner, observer)
}
