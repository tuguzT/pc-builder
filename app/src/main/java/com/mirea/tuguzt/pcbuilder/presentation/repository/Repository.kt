package com.mirea.tuguzt.pcbuilder.presentation.repository

import androidx.lifecycle.LiveData
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Base interface for all repositories.
 *
 * @see Component
 */
interface Repository<C : Component> {
    val defaultDispatcher: CoroutineDispatcher

    fun getAllComponents(): LiveData<out List<C>>

    suspend fun addComponent(component: C)

    suspend fun deleteComponent(component: C)

    suspend fun deleteAllComponents()
}
