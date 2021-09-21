package com.mirea.tuguzt.pcbuilder.presentation.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mirea.tuguzt.pcbuilder.domain.model.Size
import com.mirea.tuguzt.pcbuilder.presentation.repository.Repository
import io.nacular.measured.units.Length.Companion.centimeters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times

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
    private val data = MutableLiveData(list)

    override fun getAllComponents(): LiveData<out List<MockComponent>> = data

    override suspend fun addComponent(component: MockComponent) {
        list += component
        data.value = list
    }

    override suspend fun deleteComponent(component: MockComponent) {
        list -= component
        data.value = list
    }

    override suspend fun deleteAllComponents() {
        list.clear()
        data.value = list
    }
}
