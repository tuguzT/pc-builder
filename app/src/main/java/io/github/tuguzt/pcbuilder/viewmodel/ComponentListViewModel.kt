package io.github.tuguzt.pcbuilder.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Injectable view model with list of components.
 */
@HiltViewModel
class ComponentListViewModel @Inject constructor(
    private val componentRepository: Repository<String, Component>,
) : ViewModel() {

    val components: Flow<List<Component>> get() = componentRepository.getAll()

    suspend fun addComponent(component: Component): Unit = componentRepository.save(component)
}
