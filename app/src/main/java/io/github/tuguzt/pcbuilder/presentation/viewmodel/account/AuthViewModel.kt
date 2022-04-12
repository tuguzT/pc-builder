package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import android.app.Application
import android.content.Intent
import androidx.annotation.CheckResult
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import io.github.tuguzt.pcbuilder.presentation.model.user.*
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendAuthAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendCompletableResponse
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendResponse
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
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

    @CheckResult
    suspend fun googleOAuth2(
        application: Application,
        data: Intent?,
    ): BackendResponse<UserData> {
        val googleAccount = GoogleSignIn.getSignedInAccountFromIntent(data).await()
        val tokenData = UserTokenData(requireNotNull(googleAccount.serverAuthCode))

        val response = withContext(Dispatchers.IO) {
            backendAuthAPI.googleOAuth2(tokenData)
        }
        return when (response) {
            is NetworkResponse.Success -> {
                application.userSharedPreferences.edit {
                    putString("access_token", response.body.accessToken)
                    putString("username", googleAccount.displayName)
                }
                val user = googleAccount.toUser()
                NetworkResponse.Success(user, response.response)
            }
            is NetworkResponse.Error -> {
                @Suppress("UNCHECKED_CAST")
                response as BackendResponse<UserData>
            }
        }
    }
}
