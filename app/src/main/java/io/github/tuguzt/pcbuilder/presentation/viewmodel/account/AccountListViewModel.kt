package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import androidx.lifecycle.ViewModel
import io.github.tuguzt.pcbuilder.presentation.model.user.UserData
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendUsersAPI
import retrofit2.await

class AccountListViewModel(private val backendUsersAPI: BackendUsersAPI) : ViewModel() {
    suspend fun getAllUsers(): List<UserData> = backendUsersAPI.getAll().await()
}
