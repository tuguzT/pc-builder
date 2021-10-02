package io.github.tuguzt.pcbuilder.presentation.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.MutableRepository
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
object MockComponentRepository : MutableRepository<String, ComponentData> {
    private var list = List(20) { index ->
        ComponentData(
            id = NanoIdUtils.randomNanoId(),
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

    override val allData: LiveData<out List<ComponentData>> get() = data

    override suspend fun add(item: ComponentData) {
        list = list + item
        withContext(defaultDispatcher) {
            data.value = list
        }
    }

    override suspend fun update(item: ComponentData) {
        val index = list.indexOfFirst { it.id == item.id }
        require(index > -1) { "No such item in repository: item is $item" }
        list = list.toMutableList().apply { set(index, item) }
        withContext(defaultDispatcher) {
            data.value = list
        }
    }

    override suspend fun remove(item: ComponentData) {
        list = list - item
        withContext(defaultDispatcher) {
            data.value = list
        }
    }

    override suspend fun clear() {
        list = listOf()
        withContext(defaultDispatcher) {
            data.value = list
        }
    }
}
