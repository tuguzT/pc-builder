package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import androidx.annotation.CheckResult
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.security.crypto.EncryptedSharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.pcbuilder.data.repository.backend.BackendCompletableResponse
import io.github.tuguzt.pcbuilder.data.repository.backend.BackendUsersAPI
import io.github.tuguzt.pcbuilder.data.repository.backend.makeUnknownError
import io.github.tuguzt.pcbuilder.domain.model.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Injectable view model which holds information about current user.
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val backendUsersAPI: BackendUsersAPI,
    private val sharedPreferences: EncryptedSharedPreferences,
    private val googleSignInClient: GoogleSignInClient,
) : ViewModel() {

    private val _currentUser: MutableStateFlow<User?> = MutableStateFlow(null)
    val currentUser = _currentUser.asStateFlow()

    @CheckResult
    suspend fun updateUser(): BackendCompletableResponse {
        sharedPreferences.getString("access_token", null)
            ?: return makeUnknownError("No information about any user")

        val result = withContext(Dispatchers.IO) {
            backendUsersAPI.current()
        }
        return when (result) {
            is NetworkResponse.Success -> {
                val user = result.body
                sharedPreferences.edit {
                    putString(User::id.name, "${user.id}")
                    putString(User::imageUri.name, user.imageUri)
                    putString(User::email.name, user.email)
                    putString(User::role.name, user.role.toString())
                }
                _currentUser.emit(user)
                NetworkResponse.Success(Unit, result.response)
            }
            is NetworkResponse.Error -> {
                @Suppress("UNCHECKED_CAST")
                result as BackendCompletableResponse
            }
        }
    }

    suspend fun signOut() {
        sharedPreferences.edit { clear() }
        googleSignInClient.signOut().await()
        _currentUser.emit(null)
    }
}
