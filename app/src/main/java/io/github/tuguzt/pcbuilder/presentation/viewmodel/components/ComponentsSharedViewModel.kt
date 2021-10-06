package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.MutableRepository
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.github.tuguzt.pcbuilder.presentation.repository.mock.MockComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentAddFragment
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlinx.coroutines.launch

/**
 * [ViewModel] subclass for [ComponentAddFragment].
 */
class ComponentsSharedViewModel : ViewModel() {
    val allComponents get() = RepositoryAccess.localRepository.allData

    fun addComponent(name: String, description: String, weight: Measure<Mass>, size: Size) {
        val id = NanoIdUtils.randomNanoId()
        val repository = RepositoryAccess.localRepository
        val component = when (repository as MutableRepository<out Component>) {
            MockComponentRepository -> ComponentData(id, name, description, weight, size, null)
            else -> ComponentDto(id, name, description, weight, size, null)
        }
        viewModelScope.launch {
            repository.add(component)
        }
    }

    fun updateComponent(item: ComponentData) {
        val repository = RepositoryAccess.localRepository
        val component = when (repository as MutableRepository<out Component>) {
            MockComponentRepository -> item
            else -> ComponentDto(item)
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
