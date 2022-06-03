package io.github.tuguzt.pcbuilder.viewmodel.components

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Injectable view model with list of components.
 */
@HiltViewModel
class ComponentsViewModel @Inject constructor(
    private val componentRepository: Repository<NanoId, ComponentData>,
) : ViewModel() {

    val components: Flow<List<ComponentData>> get() = componentRepository.getAll()

    fun findById(id: NanoId): Flow<ComponentData> = componentRepository.findById(id)

    suspend fun addComponent(component: ComponentData): Unit = componentRepository.save(component)
}
