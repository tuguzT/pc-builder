package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlinx.coroutines.launch

class ComponentAddViewModel : ViewModel() {
    fun addComponent(name: String, description: String, weight: Measure<Mass>, size: Size) {
        val component =
            ComponentData(id = NanoIdUtils.randomNanoId(), name, description, weight, size)
        viewModelScope.launch {
            RepositoryAccess.localRepository.add(component)
        }
    }
}
