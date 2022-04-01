package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import androidx.lifecycle.ViewModel
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendUsersAPI

class AccountListViewModel(private val backendUsersAPI: BackendUsersAPI) : ViewModel() {
    suspend fun getAllUsers() = backendUsersAPI.getAll()
}
