package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import android.app.Application
import androidx.annotation.CheckResult
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.presentation.model.user.UserData
import io.github.tuguzt.pcbuilder.presentation.model.user.toUser
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendCompletableResponse
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendUsersAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.makeError
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * View model of 'Account' page.
 */
class AccountViewModel(
    private val googleSignInClient: GoogleSignInClient,
    private val backendUsersAPI: BackendUsersAPI,
) : ViewModel() {
    var currentUser: UserData? = null
    var username: String? = null

    @CheckResult
    suspend fun updateUserFromBackend(application: Application): BackendCompletableResponse {
        val sharedPreferences = application.userSharedPreferences
        val token = sharedPreferences.getString("access_token", null)

        token?.let {
            val response = withContext(Dispatchers.IO) {
                backendUsersAPI.current()
            }
            return when (response) {
                is NetworkResponse.Success -> {
                    val user = response.body
                    sharedPreferences.edit {
                        putString(UserData::id.name, user.id)
                        putString(UserData::imageUri.name, user.imageUri)
                        putString(UserData::email.name, user.email)
                        putString(UserData::role.name, user.role.toString())
                    }
                    currentUser = user
                    NetworkResponse.Success(Unit, response.response)
                }
                is NetworkResponse.Error -> {
                    @Suppress("UNCHECKED_CAST")
                    response as BackendCompletableResponse
                }
            }
        }

        return makeError("No information about any user")
    }

    @CheckResult
    suspend fun findUser(application: Application): BackendCompletableResponse {
        val sharedPreferences = application.userSharedPreferences

        val result = runCatching { googleSignInClient.silentSignIn().await() }
        val googleAccount: GoogleSignInAccount? = result.getOrNull()
        if (googleAccount != null) {
            TODO("send ID token to the server and validate it")
            currentUser = googleAccount.toUser()
            return NetworkResponse.Success(Unit, Response.success(null))
        }

        val accessToken = sharedPreferences.getString("access_token", null)
        if (accessToken != null) return updateUserFromBackend(application)

        return makeError("No information about any user")
    }
}
