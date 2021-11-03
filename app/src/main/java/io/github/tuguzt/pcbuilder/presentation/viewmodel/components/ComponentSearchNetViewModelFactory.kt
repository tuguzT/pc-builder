package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.OctopartAPI

/**
 * Factory for [ComponentSearchNetViewModel].
 */
class ComponentSearchNetViewModelFactory(
    private val octopartAPI: OctopartAPI,
    private val backendAPI: BackendAPI,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ComponentSearchNetViewModel(octopartAPI, backendAPI) as T
}
