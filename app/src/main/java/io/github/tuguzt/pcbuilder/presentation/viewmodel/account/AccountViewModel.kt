package io.github.tuguzt.pcbuilder.presentation.viewmodel.account

import android.app.Application
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.presentation.model.user.UserData
import io.github.tuguzt.pcbuilder.presentation.model.user.toUser
import io.github.tuguzt.pcbuilder.presentation.repository.net.BackendUsersAPI
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
                backendUsersAPI.current()
            }
            when (user) {
                is NetworkResponse.Success -> {
                    val userData = user.body
                    sharedPreferences.edit {
                        putString(UserData::id.name, userData.id)
                        putString(UserData::imageUri.name, userData.imageUri)
                        putString(UserData::email.name, userData.email)
                        putString(UserData::role.name, userData.role.toString())
                    }
                    currentUser = userData
                }
                // todo error handling
                is NetworkResponse.Error -> Log.e("TODO", "do error handling in UI")
            }
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
