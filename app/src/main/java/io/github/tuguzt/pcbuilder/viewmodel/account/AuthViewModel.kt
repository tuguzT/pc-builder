package io.github.tuguzt.pcbuilder.viewmodel.account

import androidx.annotation.CheckResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.security.crypto.EncryptedSharedPreferences
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.repository.backend.BackendAuthAPI
import io.github.tuguzt.pcbuilder.repository.backend.BackendCompletableResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Injectable view model for user authentication.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val backendAuthAPI: BackendAuthAPI,
    private val sharedPreferences: EncryptedSharedPreferences,
) : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)

    @CheckResult
    suspend fun auth(credentials: UserCredentialsData): BackendCompletableResponse {
        val userToken = withContext(Dispatchers.IO) {
            backendAuthAPI.auth(credentials)
        }
        return when (userToken) {
            is NetworkResponse.Success -> {
                withContext(Dispatchers.IO) {
                    sharedPreferences.edit {
                        putString("access_token", userToken.body.accessToken)
                        putString(UserCredentials::username.name, credentials.username)
                    }
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
    suspend fun register(user: UserCredentialsData): BackendCompletableResponse {
        val userToken = withContext(Dispatchers.IO) {
            backendAuthAPI.register(user)
        }
        return when (userToken) {
            is NetworkResponse.Success -> {
                withContext(Dispatchers.IO) {
                    sharedPreferences.edit {
                        putString("access_token", userToken.body.accessToken)
                    }
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
