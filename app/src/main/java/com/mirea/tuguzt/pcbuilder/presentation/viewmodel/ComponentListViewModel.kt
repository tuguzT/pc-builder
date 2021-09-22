package com.mirea.tuguzt.pcbuilder.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import com.mirea.tuguzt.pcbuilder.presentation.view.ComponentAddFragment
import kotlinx.coroutines.launch

/**
 * [ViewModel] subclass for [ComponentAddFragment].
 */
class ComponentListViewModel : ViewModel() {
    fun getAllComponents(): LiveData<out List<Component>> {
        return RepositoryAccess.localRepository.getAllComponents()
    }

    fun deleteComponent(component: Component) {
        viewModelScope.launch {
            RepositoryAccess.localRepository.deleteComponent(component)
        }
    }

    fun deleteAllComponents() {
        viewModelScope.launch {
            RepositoryAccess.localRepository.deleteAllComponents()
        }
    }
}
