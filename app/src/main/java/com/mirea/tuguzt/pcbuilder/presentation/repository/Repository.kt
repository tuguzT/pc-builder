package com.mirea.tuguzt.pcbuilder.presentation.repository

import androidx.lifecycle.LiveData
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO

/**
 * Base interface for all repositories.
 */
interface Repository {
    fun getAllComponents(): LiveData<out List<Component>>

    suspend fun addComponent(component: ComponentDTO)

    suspend fun deleteComponent(component: ComponentDTO)

    suspend fun deleteAllComponents()
}
