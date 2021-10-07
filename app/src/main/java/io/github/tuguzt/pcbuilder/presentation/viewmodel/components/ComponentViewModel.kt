package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess

class ComponentViewModel(id: String) : ViewModel() {
    val component = RepositoryAccess.localRepository.findById(id)
}