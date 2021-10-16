package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentAddFragment
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure

/**
 * View model for [ComponentAddFragment].
 */
class ComponentsSharedViewModel : ViewModel() {
    val allComponents get() = RepositoryAccess.localRepository.allData

    fun addComponent(
        name: String,
        description: String,
        weight: Measure<Mass>,
        size: Size,
        imageUri: String? = null,
    ) {
        val id = NanoIdUtils.randomNanoId()
        val component = ComponentData(id, name, description, weight, size, imageUri)
        RepositoryAccess.localRepository.add(component)
    }

    fun updateComponent(item: Component) {
        RepositoryAccess.localRepository.update(item)
    }

    fun deleteComponent(component: Component) {
        RepositoryAccess.localRepository.remove(component)
    }

    fun deleteAllComponents() {
        RepositoryAccess.localRepository.clear()
    }
}
