package com.mirea.tuguzt.pcbuilder.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mirea.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import com.mirea.tuguzt.pcbuilder.presentation.repository.dto.ComponentDTO

class ComponentListViewModel : ViewModel() {
    fun getAllComponents(): LiveData<List<ComponentDTO>> {
        return RepositoryAccess.roomRepository.getAllComponents()
    }

    fun deleteComponent(component: ComponentDTO) {
        launchIO {
            RepositoryAccess.roomRepository.deleteComponent(component)
        }
    }

    fun deleteAllComponents() {
        launchIO {
            RepositoryAccess.roomRepository.deleteAllComponents()
        }
    }
}
