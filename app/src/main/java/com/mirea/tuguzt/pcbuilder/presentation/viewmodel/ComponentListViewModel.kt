package com.mirea.tuguzt.pcbuilder.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirea.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComponentListViewModel : ViewModel() {
    fun getAllComponents(): LiveData<List<ComponentDTO>> {
        return RepositoryAccess.roomRepository.getAllComponents()
    }

    fun deleteComponent(component: ComponentDTO) {
        viewModelScope.launch(context = Dispatchers.IO) {
            RepositoryAccess.roomRepository.deleteComponent(component)
        }
    }

    fun deleteAllComponents() {
        viewModelScope.launch(context = Dispatchers.IO) {
            RepositoryAccess.roomRepository.deleteAllComponents()
        }
    }
}
