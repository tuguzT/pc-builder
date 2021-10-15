package io.github.tuguzt.pcbuilder.presentation.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.component.ComponentDto
import io.nacular.measured.units.Length.Companion.centimeters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Mock repository of [components][Component].
 *
 * @see Component
 */
object MockComponentRepository : Repository<String, Component> {
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
            imageUri = null,
        )
    }
    private val data = MutableLiveData(list)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override val allData: LiveData<out List<Component>> get() = data

    override fun findById(id: String): LiveData<out Component> =
        MutableLiveData<ComponentData>().apply {
            data.observeForever { components ->
                value = components.find { it.id == id }
            }
        }

    override fun add(item: Component) {
        val component = when (item) {
            is ComponentData -> item
            is ComponentDto -> ComponentData(item)
            else -> throw IllegalStateException(
                "Data loss: item must be convertible to ${ComponentData::class.qualifiedName}"
            )
        }
        list = list + component
        coroutineScope.launch {
            data.value = list
        }
    }

    override fun update(item: Component) {
        val component = when (item) {
            is ComponentData -> item
            is ComponentDto -> ComponentData(item)
            else -> throw IllegalStateException(
                "Data loss: item must be convertible to ${ComponentData::class.qualifiedName}"
            )
        }
        val index = list.indexOfFirst { it.id == component.id }
        require(index > -1) { "No such item in repository: item is $component" }

        list = list.toMutableList().apply { set(index, component) }
        coroutineScope.launch {
            data.value = list
        }
    }

    override fun remove(item: Component) {
        val component = when (item) {
            is ComponentData -> item
            is ComponentDto -> ComponentData(item)
            else -> throw IllegalStateException(
                "Data loss: item must be convertible to ${ComponentData::class.qualifiedName}"
            )
        }
        list = list - component
        coroutineScope.launch {
            data.value = list
        }
    }

    override fun clear() {
        list = listOf()
        coroutineScope.launch {
            data.value = list
        }
    }
}
