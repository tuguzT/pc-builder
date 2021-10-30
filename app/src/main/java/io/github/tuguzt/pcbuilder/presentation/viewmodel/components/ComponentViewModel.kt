package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentFragment

/**
 * View model of component displayed in [ComponentFragment].
 */
class ComponentViewModel(id: String) : ViewModel() {
    val component = RepositoryAccess.localComponentRepository.findById(id)
}
