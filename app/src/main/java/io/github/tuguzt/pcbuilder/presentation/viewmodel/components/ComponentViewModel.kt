package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentFragment

/**
 * View model of component displayed in [ComponentFragment].
 */
class ComponentViewModel(
    id: String,
    componentRepository: Repository<out Component, String>,
) : ViewModel() {
    val component: LiveData<out Component> = componentRepository.findById(id)
}
