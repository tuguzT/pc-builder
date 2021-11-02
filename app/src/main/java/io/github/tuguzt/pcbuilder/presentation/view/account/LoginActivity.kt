package io.github.tuguzt.pcbuilder.presentation.view.account

import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import io.github.tuguzt.pcbuilder.databinding.ActivityLoginBinding
import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.presentation.model.user.UserSealed
import io.github.tuguzt.pcbuilder.presentation.model.user.toUser
import io.github.tuguzt.pcbuilder.presentation.model.user.user
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.github.tuguzt.pcbuilder.presentation.view.googleSignInOptions
import io.github.tuguzt.pcbuilder.presentation.view.snackbarShort
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Activity for user login.
 */
class LoginActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        private val LOG_TAG = LoginActivity::class.simpleName
    }

    private lateinit var _binding: ActivityLoginBinding
    private inline val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        val contract = ActivityResultContracts.StartActivityForResult()
        val googleSignInLauncher = registerForActivityResult(contract) {
            if (it.resultCode != RESULT_OK) {
                snackbarShort(binding.root) { "User was not signed in!" }.show()
                return@registerForActivityResult
            }
            lifecycleScope.launch {
                try {
                    val data = it.data
                    val googleAccount = GoogleSignIn.getSignedInAccountFromIntent(data).await()
                    userSharedPreferences.edit {
                        putString("google-token", googleAccount.idToken)
                    }

                    val user = googleAccount.toUser()
                    resultUser(user)
                } catch (exception: ApiException) {
                    val message = "Google authorization failed"
                    Log.e(LOG_TAG, message, exception)
                    snackbarShort(binding.root) { message }.show()
                }
            }
        }

        binding.run {
            googleButton.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                googleSignInLauncher.launch(signInIntent)
            }

            @Suppress("NAME_SHADOWING")
            signIn.setOnClickListener {
                val username = username.text.toString()
                val password = password.text.toString()
                if (username.isNotBlank() && password.isNotBlank()) {
                    val username = username.trim()
                    val password = password.trim()
                    if (checkUsername(username) && checkPassword(password)) {
                        val user = user(username, "null@null.null", password, null)
                        resultUser(user)
                        return@setOnClickListener
                    }
                    snackbarShort(root) { "Incorrect input for username/password!" }.show()
                    return@setOnClickListener
                }
                snackbarShort(root) { "Username/password are empty!" }.show()
            }
        }
    }

    private fun resultUser(user: UserSealed) {
        RepositoryAccess.setUser(user, this)
        setResult(RESULT_OK)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
    }
}
