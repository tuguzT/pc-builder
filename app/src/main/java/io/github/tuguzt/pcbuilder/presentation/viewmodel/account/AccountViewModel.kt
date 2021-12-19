package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import io.github.tuguzt.pcbuilder.presentation.model.user.UserData
import io.github.tuguzt.pcbuilder.presentation.model.user.toUser
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendUsersAPI
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

/**
 * View model of 'Account' page.
 */
class AccountViewModel(private val backendUsersAPI: BackendUsersAPI) : ViewModel() {
    var currentUser: UserData? = null
    var username: String? = null

    suspend fun updateUserRemote(application: Application): UserData? {
        val sharedPreferences = application.userSharedPreferences
        val token = sharedPreferences.getString("access_token", null)

        token?.let {
            val user = withContext(Dispatchers.IO) {
                backendUsersAPI.current().await()
            }
            sharedPreferences.edit {
                putString(UserData::id.name, user.id)
                putString(UserData::imageUri.name, user.imageUri)
                putString(UserData::email.name, user.email)
                putString(UserData::role.name, user.role.toString())
            }
            currentUser = user
        }
        return currentUser
    }

    suspend fun findUser(application: Application): UserData? {
        val sharedPreferences = application.userSharedPreferences

        val googleAccount = GoogleSignIn.getLastSignedInAccount(application)
        val userFromGoogle = googleAccount?.toUser()
        if (userFromGoogle != null) {
            sharedPreferences.edit {
                putString("google_token", googleAccount.idToken)
            }
            currentUser = userFromGoogle
            return currentUser
        }

        val accessToken = sharedPreferences.getString("access_token", null)
        if (accessToken != null) {
            currentUser = updateUserRemote(application)
        }
        return currentUser
    }
}
