package com.mirea.tuguzt.pcbuilder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.domain.model.Size
import com.mirea.tuguzt.pcbuilder.presentation.repository.Repository
import com.mirea.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import com.mirea.tuguzt.pcbuilder.presentation.repository.mock.MockComponent
import com.mirea.tuguzt.pcbuilder.presentation.repository.mock.MockComponentRepository
import com.mirea.tuguzt.pcbuilder.presentation.repository.room.RoomComponentRepository
import com.mirea.tuguzt.pcbuilder.presentation.repository.room.dto.ComponentDTO
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure

class ComponentAddViewModel : ViewModel() {
    fun addComponent(name: String, description: String, weight: Measure<Mass>, size: Size) {
        val localRepository = RepositoryAccess.localRepository
        val component: Component = when (localRepository as Repository<out Component>) {
            is RoomComponentRepository -> ComponentDTO(name, description, weight, size)
            MockComponentRepository -> MockComponent(name, description, weight, size)
            else -> throw IllegalStateException("Unknown type of local repository!!!")
        }
        launchIO {
            localRepository.addComponent(component)
        }
    }
}
