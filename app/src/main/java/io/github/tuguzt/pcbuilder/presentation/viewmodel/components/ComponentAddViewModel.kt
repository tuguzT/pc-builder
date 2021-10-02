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
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlinx.coroutines.launch

class ComponentAddViewModel : ViewModel() {
    fun addComponent(name: String, description: String, weight: Measure<Mass>, size: Size) {
        val id = NanoIdUtils.randomNanoId()
        val repository = RepositoryAccess.localRepository
        val component = when (repository as MutableRepository<out Component>) {
            is MockComponentRepository -> ComponentData(id, name, description, weight, size, null)
            else -> ComponentDto(id, name, description, weight, size, null)
        }
        viewModelScope.launch {
            repository.add(component)
        }
    }
}
