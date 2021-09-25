package io.github.tuguzt.pcbuilder.presentation.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.tuguzt.pcbuilder.domain.model.Size
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.nacular.measured.units.Length.Companion.centimeters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Mock repository of [components][ComponentData].
 *
 * @see ComponentData
 */
object MockComponentRepository : Repository<ComponentData> {
    private var list = List(20) { index ->
        ComponentData(
            name = "name $index",
            description = "description $index",
            weight = index * grams,
            size = Size(
                length = index * centimeters,
                width = index * centimeters,
                height = index * centimeters,
            ),
        )
    }
    private val data = MutableLiveData(list)

    override val defaultDispatcher = Dispatchers.Main

    override val allComponents: LiveData<out List<ComponentData>> get() = data

    override suspend fun addComponent(component: ComponentData) = withContext(defaultDispatcher) {
        list = list + component
        data.value = list
    }

    override suspend fun deleteComponent(component: ComponentData) =
        withContext(defaultDispatcher) {
            list = list - component
            data.value = list
        }

    override suspend fun deleteAllComponents() = withContext(defaultDispatcher) {
        list = listOf()
        data.value = list
    }
}
