package io.github.tuguzt.pcbuilder.presentation.view.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import io.github.tuguzt.pcbuilder.databinding.ActivityLoginBinding
import io.github.tuguzt.pcbuilder.presentation.view.googleSignInOptions
import io.github.tuguzt.pcbuilder.presentation.view.snackbarShort
import io.github.tuguzt.pcbuilder.presentation.view.toastShort
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

    private lateinit var _googleSignInClient: GoogleSignInClient
    private inline val googleSignInClient get() = _googleSignInClient

    private lateinit var _googleSignInLauncher: ActivityResultLauncher<Intent>
    private inline val googleSignInLauncher get() = _googleSignInLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        _googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        val contract = ActivityResultContracts.StartActivityForResult()
        _googleSignInLauncher = registerForActivityResult(contract) {
            if (it.resultCode != RESULT_OK) {
                toastShort(applicationContext) { "User was not signed in!" }.show()
                return@registerForActivityResult
            }
            lifecycleScope.launch {
                try {
                    val data = it.data
                    val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
                    Log.i(LOG_TAG, "name=${account.displayName} email=${account.email}")
                    setResult(RESULT_OK, data)
                    finish()
                } catch (exception: ApiException) {
                    val message = "Google authorization failed"
                    Log.e(LOG_TAG, message, exception)
                    snackbarShort(binding.root) { message }.show()
                }
            }
        }

        binding.googleButton.setOnClickListener(this::signInGoogle)
    }

    private fun signInGoogle(@Suppress("UNUSED_PARAMETER") view: View) {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
    }
}
