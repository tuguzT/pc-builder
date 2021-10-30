package io.github.tuguzt.pcbuilder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import io.github.tuguzt.pcbuilder.databinding.ActivityMainBinding
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.github.tuguzt.pcbuilder.presentation.view.account.LoginActivity
import io.github.tuguzt.pcbuilder.presentation.view.snackbarShort
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Entry point of the application.
 */
class MainActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        private val LOG_TAG = MainActivity::class.simpleName
    }

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    private val navController: NavController
        get() {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
            return navHostFragment.navController
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        RepositoryAccess.initRoom(application)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            val loginIntent = Intent(this, LoginActivity::class.java)
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_CANCELED) {
                    finish()
                    return@registerForActivityResult
                }
                lifecycleScope.launch {
                    try {
                        @Suppress("NAME_SHADOWING")
                        val account = GoogleSignIn.getSignedInAccountFromIntent(it.data).await()
                        Log.i(LOG_TAG, "name=${account.displayName} email=${account.email}")
                    } catch (exception: ApiException) {
                        val message = "Google authorization failed"
                        Log.e(LOG_TAG, message, exception)
                        snackbarShort(binding.root) { message }.show()
                    }
                }
            }.launch(loginIntent)
        }

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.bottomNavigation.setupWithNavController(navController)
    }
}
