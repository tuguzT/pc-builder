package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.repository.Repository

/**
 * Factory for [ComponentsSharedViewModel].
 */
class ComponentsSharedViewModelFactory(
    private val localComponentRepository: Repository<String, Component>,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ComponentsSharedViewModel(localComponentRepository) as T
}
