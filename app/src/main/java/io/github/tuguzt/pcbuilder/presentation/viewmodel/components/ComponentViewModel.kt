package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentFragment

/**
 * View model of component displayed in [ComponentFragment].
 */
class ComponentViewModel(
    id: String,
    componentRepository: Repository<String, Component>,
) : ViewModel() {
    val component = componentRepository.findById(id)
}
