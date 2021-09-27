package io.github.tuguzt.pcbuilder.presentation.repository

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.domain.model.Component
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
