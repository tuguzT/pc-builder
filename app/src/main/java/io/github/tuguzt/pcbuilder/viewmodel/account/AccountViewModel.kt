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
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.repository.backend.BackendCompletableResponse
import io.github.tuguzt.pcbuilder.repository.backend.BackendUsersAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

/**
 * Injectable view model which holds information about current user.
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val backendUsersAPI: BackendUsersAPI,
    private val sharedPreferences: EncryptedSharedPreferences,
) : ViewModel() {

    var currentUser: User? by mutableStateOf(null)

    @CheckResult
    suspend fun updateUser(): BackendCompletableResponse {
        sharedPreferences.getString("access_token", null) ?: return makeUnknownError()

        val result = withContext(Dispatchers.IO) {
            backendUsersAPI.current()
        }
        return when (result) {
            is NetworkResponse.Success -> {
                val user = result.body
                sharedPreferences.edit {
                    putString(User::id.name, user.id)
                    putString(User::imageUri.name, user.imageUri)
                    putString(User::email.name, user.email)
                    putString(User::role.name, user.role.toString())
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

    @CheckResult
    suspend fun findUser(): BackendCompletableResponse {
        sharedPreferences.getString("access_token", null) ?: return makeUnknownError()
        return updateUser()
    }

    companion object {
        @JvmStatic
        private fun makeUnknownError(): BackendCompletableResponse {
            val errorResponse = Response.error<Nothing>(500, "".toResponseBody())
            val error = Exception("No information about any user")
            return NetworkResponse.UnknownError(error, errorResponse)
        }
    }
}
