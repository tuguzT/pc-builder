package io.github.tuguzt.pcbuilder.presentation.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Base interface for all repositories.
 *
 * @see Component
 */
interface Repository<C : Component> {
    val defaultDispatcher: CoroutineDispatcher

    val allComponents: LiveData<out List<C>>

    suspend fun add(component: Component)

    suspend fun remove(component: C)

    suspend fun clear()
}

fun <C : Component> Repository<C>.findById(id: String, owner: LifecycleOwner): LiveData<Component> =
    MutableLiveData<Component>().apply {
        allComponents.observe(owner) { components ->
            value = components.find { it.id == id }
        }
    }
