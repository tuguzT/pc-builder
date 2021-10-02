package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentAddFragment
import kotlinx.coroutines.launch

/**
 * [ViewModel] subclass for [ComponentAddFragment].
 */
class ComponentListViewModel : ViewModel() {
    val allComponents = RepositoryAccess.localRepository.allData

    fun deleteComponent(component: Component) {
        viewModelScope.launch {
            RepositoryAccess.localRepository.remove(component)
        }
    }

    fun deleteAllComponents() {
        viewModelScope.launch {
            RepositoryAccess.localRepository.clear()
        }
    }
}
