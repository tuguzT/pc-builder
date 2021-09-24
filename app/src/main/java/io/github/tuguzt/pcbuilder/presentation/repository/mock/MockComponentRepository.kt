package io.github.tuguzt.pcbuilder.presentation.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.tuguzt.pcbuilder.domain.model.Size
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.nacular.measured.units.Length.Companion.centimeters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Mock repository of [components][MockComponent].
 *
 * @see MockComponent
 */
object MockComponentRepository : Repository<MockComponent> {
    private var list = List(20) { index ->
        MockComponent(
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

    override val allComponents: LiveData<out List<MockComponent>> get() = data

    override suspend fun addComponent(component: MockComponent) = withContext(defaultDispatcher) {
        list = list + component
        data.value = list
    }

    override suspend fun deleteComponent(component: MockComponent) =
        withContext(defaultDispatcher) {
            list = list - component
            data.value = list
        }

    override suspend fun deleteAllComponents() = withContext(defaultDispatcher) {
        list = listOf()
        data.value = list
    }
}
