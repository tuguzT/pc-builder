package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import io.github.tuguzt.pcbuilder.presentation.model.user.UserCredentialsData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserNamePasswordData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserTokenData
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendAuthAPI
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class AccountAuthViewModel(private val backendAuthAPI: BackendAuthAPI) : ViewModel() {
    suspend fun auth(application: Application, credentials: UserCredentialsData) {
        val userToken = withContext(Dispatchers.IO) {
            backendAuthAPI.auth(credentials).await()
        }
        application.userSharedPreferences.edit {
            putString("access_token", userToken.accessToken)
            putString("username", credentials.username)
        }
    }

    suspend fun register(user: UserNamePasswordData) {
        TODO()
    }

    suspend fun oauth2(token: UserTokenData) {
        TODO()
    }
}
