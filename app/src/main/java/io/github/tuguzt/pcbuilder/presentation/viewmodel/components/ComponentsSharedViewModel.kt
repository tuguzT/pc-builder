package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import io.github.tuguzt.pcbuilder.presentation.model.component.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentAddFragment
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure

/**
 * View model for [ComponentAddFragment].
 */
class ComponentsSharedViewModel(
    private val componentRepository: Repository<String, Component>,
) : ViewModel() {
    val allComponents get() = componentRepository.allData

    fun addComponent(
        name: String,
        description: String,
        weight: Measure<Mass>,
        size: Size,
        imageUri: String? = null,
    ) {
        val id = randomNanoId()
        val component = ComponentData(id, name, description, weight, size, imageUri)
        componentRepository.add(component)
    }

    fun updateComponent(item: Component) {
        componentRepository.update(item)
    }

    fun deleteComponent(component: Component) {
        componentRepository.remove(component)
    }

    fun deleteAllComponents() {
        componentRepository.clear()
    }
}
