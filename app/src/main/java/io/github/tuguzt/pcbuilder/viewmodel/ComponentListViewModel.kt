package io.github.tuguzt.pcbuilder.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Injectable view model with list of components.
 */
@HiltViewModel
class ComponentListViewModel @Inject constructor() : ViewModel() {
    private val _components = MutableStateFlow(listOf<Component>())
    val components get() = _components.asStateFlow()

    operator fun plusAssign(component: Component) {
        _components.update { it + component }
    }
}
