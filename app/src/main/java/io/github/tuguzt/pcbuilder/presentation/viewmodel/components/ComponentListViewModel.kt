package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.MutableRepository
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.github.tuguzt.pcbuilder.presentation.repository.mock.MockComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentAddFragment
import kotlinx.coroutines.launch

/**
 * [ViewModel] subclass for [ComponentAddFragment].
 */
class ComponentListViewModel : ViewModel() {
    val allComponents = RepositoryAccess.localRepository.allData

    fun updateComponent(item: ComponentData) {
        val repository = RepositoryAccess.localRepository
        val component = when (repository as MutableRepository<out Component>) {
            is MockComponentRepository -> ComponentData(item.id,
                item.name,
                item.description,
                item.weight,
                item.size,
                item.imageUri,
            )
            else -> ComponentDto(item.id,
                item.name,
                item.description,
                item.weight,
                item.size,
                item.imageUri,
            )
        }
        viewModelScope.launch {
            repository.update(component)
        }
    }

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
