package io.github.tuguzt.pcbuilder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.tuguzt.pcbuilder.domain.model.Component
import io.github.tuguzt.pcbuilder.domain.model.Size
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.github.tuguzt.pcbuilder.presentation.repository.mock.MockComponent
import io.github.tuguzt.pcbuilder.presentation.repository.mock.MockComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDto
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlinx.coroutines.launch

class ComponentAddViewModel : ViewModel() {
    fun addComponent(name: String, description: String, weight: Measure<Mass>, size: Size) {
        val localRepository = RepositoryAccess.localRepository
        val component: Component = when (localRepository as Repository<out Component>) {
            is RoomComponentRepository -> ComponentDto(name, description, weight, size)
            MockComponentRepository -> MockComponent(name, description, weight, size)
            else -> throw IllegalStateException("Unknown type of local repository!!!")
        }
        viewModelScope.launch {
            localRepository.addComponent(component)
        }
    }
}
