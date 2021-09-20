package com.mirea.tuguzt.pcbuilder.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import com.mirea.tuguzt.pcbuilder.presentation.view.ComponentAddFragment

/**
 * [ViewModel] subclass for [ComponentAddFragment].
 */
class ComponentListViewModel : ViewModel() {
    fun getAllComponents(): LiveData<out List<Component>> {
        return RepositoryAccess.localRepository.getAllComponents()
    }

    fun deleteComponent(component: Component) {
        launchIO {
            RepositoryAccess.localRepository.deleteComponent(component)
        }
    }

    fun deleteAllComponents() {
        launchIO {
            RepositoryAccess.localRepository.deleteAllComponents()
        }
    }
}
