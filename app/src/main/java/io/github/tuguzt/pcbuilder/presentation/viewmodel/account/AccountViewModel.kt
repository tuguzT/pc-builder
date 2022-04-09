package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import android.app.Application
import androidx.annotation.CheckResult
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.presentation.model.user.UserData
import io.github.tuguzt.pcbuilder.presentation.model.user.toUser
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendCompletableResponse
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendUsersAPI
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

/**
 * View model of 'Account' page.
 */
class AccountViewModel(private val backendUsersAPI: BackendUsersAPI) : ViewModel() {
    var currentUser: UserData? = null
    var username: String? = null

    @CheckResult
    suspend fun updateUserFromBackend(application: Application): BackendCompletableResponse {
        val sharedPreferences = application.userSharedPreferences
        val token = sharedPreferences.getString("access_token", null)

        token?.let {
            val result = withContext(Dispatchers.IO) {
                backendUsersAPI.current()
            }
            return when (result) {
                is NetworkResponse.Success -> {
                    val user = result.body
                    sharedPreferences.edit {
                        putString(UserData::id.name, user.id)
                        putString(UserData::imageUri.name, user.imageUri)
                        putString(UserData::email.name, user.email)
                        putString(UserData::role.name, user.role.toString())
                    }
                    currentUser = user
                    NetworkResponse.Success(Unit, result.response)
                }
                is NetworkResponse.Error -> {
                    @Suppress("UNCHECKED_CAST")
                    result as BackendCompletableResponse
                }
            }
        }
        val errorResponse = Response.error<Nothing>(500, "".toResponseBody())
        val error = Exception("No information about any user")
        return NetworkResponse.UnknownError(error, errorResponse)
    }

    @CheckResult
    suspend fun findUser(application: Application): BackendCompletableResponse {
        val sharedPreferences = application.userSharedPreferences

        val googleAccount = GoogleSignIn.getLastSignedInAccount(application)
        val userFromGoogle = googleAccount?.toUser()
        if (userFromGoogle != null) {
            sharedPreferences.edit {
                putString("google_token", googleAccount.idToken)
            }
            currentUser = userFromGoogle
            return NetworkResponse.Success(Unit, Response.success(null))
        }

        val accessToken = sharedPreferences.getString("access_token", null)
        if (accessToken != null) return updateUserFromBackend(application)

        val errorResponse = Response.error<Nothing>(500, "".toResponseBody())
        val error = Exception("No information about any user")
        return NetworkResponse.UnknownError(error, errorResponse)
    }
}
