package com.mirea.tuguzt.pcbuilder.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirea.tuguzt.pcbuilder.MainActivity
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComponentListViewModel : ViewModel() {
    fun getAllComponents(): LiveData<out List<Component>> {
        return MainActivity.repository.getAllComponents()
    }

    fun deleteComponent(component: ComponentDTO) {
        viewModelScope.launch(context = Dispatchers.IO) {
            MainActivity.repository.deleteComponent(component)
        }
    }

    fun deleteAllComponents() {
        viewModelScope.launch(context = Dispatchers.IO) {
            MainActivity.repository.deleteAllComponents()
        }
    }
}
