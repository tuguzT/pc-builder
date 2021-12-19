package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import io.github.tuguzt.pcbuilder.presentation.model.user.UserCredentialsData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserNamePasswordData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserTokenData
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendAuthAPI
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class AuthViewModel(private val backendAuthAPI: BackendAuthAPI) : ViewModel() {
    suspend fun auth(application: Application, credentials: UserCredentialsData) {
        val userToken = withContext(Dispatchers.IO) {
            backendAuthAPI.auth(credentials).await()
        }
        application.userSharedPreferences.edit {
            putString("access_token", userToken.accessToken)
            putString(UserNamePassword::username.name, credentials.username)
        }
    }

    suspend fun register(application: Application, user: UserNamePasswordData) {
        val userToken = withContext(Dispatchers.IO) {
            backendAuthAPI.register(user).await()
        }
        application.userSharedPreferences.edit {
            putString("access_token", userToken.accessToken)
            putString(UserNamePassword::username.name, user.username)
            putString(User::imageUri.name, user.imageUri)
            putString(User::email.name, user.email)
            putString(User::role.name, user.role.toString())
        }
    }

    suspend fun oauth2(token: UserTokenData) {
        TODO()
    }
}
