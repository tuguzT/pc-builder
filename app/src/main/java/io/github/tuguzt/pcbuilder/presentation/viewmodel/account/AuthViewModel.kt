package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import android.app.Application
import androidx.annotation.CheckResult
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import io.github.tuguzt.pcbuilder.presentation.model.user.UserCredentialsData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserNamePasswordData
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendAuthAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendCompletableResponse
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(private val backendAuthAPI: BackendAuthAPI) : ViewModel() {
    @CheckResult
    suspend fun auth(
        application: Application,
        credentials: UserCredentialsData,
    ): BackendCompletableResponse {
        val userToken = withContext(Dispatchers.IO) {
            backendAuthAPI.auth(credentials)
        }
        return when (userToken) {
            is NetworkResponse.Success -> {
                application.userSharedPreferences.edit {
                    putString("access_token", userToken.body.accessToken)
                    putString(UserNamePassword::username.name, credentials.username)
                }
                NetworkResponse.Success(Unit, userToken.response)
            }
            is NetworkResponse.Error -> {
                @Suppress("UNCHECKED_CAST")
                userToken as BackendCompletableResponse
            }
        }
    }

    @CheckResult
    suspend fun register(
        application: Application,
        user: UserNamePasswordData,
    ): BackendCompletableResponse {
        val userToken = withContext(Dispatchers.IO) {
            backendAuthAPI.register(user)
        }
        return when (userToken) {
            is NetworkResponse.Success -> {
                application.userSharedPreferences.edit {
                    putString("access_token", userToken.body.accessToken)
                    putString(UserNamePassword::username.name, user.username)
                    putString(User::imageUri.name, user.imageUri)
                    putString(User::email.name, user.email)
                    putString(User::role.name, user.role.toString())
                }
                NetworkResponse.Success(Unit, userToken.response)
            }
            is NetworkResponse.Error -> {
                @Suppress("UNCHECKED_CAST")
                userToken as BackendCompletableResponse
            }
        }
    }
}
