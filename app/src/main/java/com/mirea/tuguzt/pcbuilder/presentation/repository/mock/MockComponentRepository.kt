package com.mirea.tuguzt.pcbuilder.presentation.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mirea.tuguzt.pcbuilder.domain.model.Size
import com.mirea.tuguzt.pcbuilder.presentation.repository.Repository
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
    private val list = MutableList(20) { index ->
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
    private val data = MutableLiveData(list as List<MockComponent>)

    override val defaultDispatcher = Dispatchers.Main

    override fun getAllComponents(): LiveData<out List<MockComponent>> = data

    override suspend fun addComponent(component: MockComponent) = withContext(defaultDispatcher) {
        list += component
        data.value = list.toMutableList()
    }

    override suspend fun deleteComponent(component: MockComponent) = withContext(defaultDispatcher) {
        list -= component
        data.value = list.toMutableList()
    }

    override suspend fun deleteAllComponents() = withContext(defaultDispatcher) {
        list.clear()
        data.value = list.toMutableList()
    }
}
