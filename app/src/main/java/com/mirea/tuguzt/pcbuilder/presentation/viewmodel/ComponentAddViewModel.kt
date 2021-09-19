package com.mirea.tuguzt.pcbuilder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirea.tuguzt.pcbuilder.domain.model.Size
import com.mirea.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComponentAddViewModel : ViewModel() {
    fun addComponent(name: String, description: String, weight: Measure<Mass>, size: Size) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val component = ComponentDTO(
                name = name,
                description = description,
                weight = weight,
                size = size,
            )
            RepositoryAccess.roomRepository.addComponent(component)
        }
    }
}
