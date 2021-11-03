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
    private val localComponentRepository: Repository<String, Component>,
) : ViewModel() {
    val allComponents get() = localComponentRepository.allData

    fun addComponent(
        name: String,
        description: String,
        weight: Measure<Mass>,
        size: Size,
        imageUri: String? = null,
    ) {
        val id = randomNanoId()
        val component = ComponentData(id, name, description, weight, size, imageUri)
        localComponentRepository.add(component)
    }

    fun updateComponent(item: Component) {
        localComponentRepository.update(item)
    }

    fun deleteComponent(component: Component) {
        localComponentRepository.remove(component)
    }

    fun deleteAllComponents() {
        localComponentRepository.clear()
    }
}
