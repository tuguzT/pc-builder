package io.github.tuguzt.pcbuilder.presentation.view.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import io.github.tuguzt.pcbuilder.databinding.ActivityLoginBinding

/**
 * Activity for user login.
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityLoginBinding
    private val binding get() = _binding

    private lateinit var _googleSignInClient: GoogleSignInClient
    private inline val googleSignInClient get() = _googleSignInClient

    private lateinit var _googleSignInLauncher: ActivityResultLauncher<Intent>
    private val googleSignInLauncher get() = _googleSignInLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        _googleSignInClient = GoogleSignIn.getClient(this, gso)

        val contract = ActivityResultContracts.StartActivityForResult()
        _googleSignInLauncher = registerForActivityResult(contract) {
            setResult(RESULT_OK, it.data)
            finish()
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
