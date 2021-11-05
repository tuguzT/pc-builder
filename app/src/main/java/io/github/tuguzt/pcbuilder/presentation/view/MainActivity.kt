package io.github.tuguzt.pcbuilder.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.ActivityMainBinding
import io.github.tuguzt.pcbuilder.presentation.model.user.toUser
import io.github.tuguzt.pcbuilder.presentation.model.user.user
import io.github.tuguzt.pcbuilder.presentation.view.account.LoginActivity
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Entry point of the application.
 */
class MainActivity : AppCompatActivity() {
    private val accountViewModel: AccountViewModel by viewModel()

    private lateinit var _binding: ActivityMainBinding
    private inline val binding get() = _binding

    private val navController: NavController
        get() {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
            return navHostFragment.navController
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        handleUser()

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun handleUser() {
        val sharedPreferences = userSharedPreferences

        val googleAccount = GoogleSignIn.getLastSignedInAccount(this)
        val userFromGoogle = googleAccount?.toUser()
        if (userFromGoogle != null) {
            sharedPreferences.edit {
                putString("google-token", googleAccount.idToken)
            }
            accountViewModel.currentUser = userFromGoogle
            return
        }

        val username = sharedPreferences.getString("username", null)
        if (username != null) {
            val email = sharedPreferences.getString("email", null)!!
            val password = sharedPreferences.getString("password", null)!!
            val imageUri = sharedPreferences.getString("imageUri", null)
            val user = user(
                username = username,
                email = email,
                password = password,
                imageUri = imageUri?.let { Uri.parse(it) }
            )
            accountViewModel.currentUser = user
            return
        }

        val loginIntent = Intent(this, LoginActivity::class.java)
        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) {
            if (it.resultCode == RESULT_CANCELED) {
                finish()
                return@registerForActivityResult
            }
            val user = it.data?.extras?.toUser()
            accountViewModel.currentUser = user
        }.launch(loginIntent)
    }
}
