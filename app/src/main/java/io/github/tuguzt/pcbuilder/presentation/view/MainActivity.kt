package io.github.tuguzt.pcbuilder.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.haroldadmin.cnradapter.NetworkResponse
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.ActivityMainBinding
import io.github.tuguzt.pcbuilder.presentation.view.account.AuthActivity
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountViewModel
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Entry point of the application.
 */
class MainActivity : AppCompatActivity() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private val accountViewModel: AccountViewModel by viewModel()

    private lateinit var _binding: ActivityMainBinding
    private inline val binding get() = _binding

    private val launcher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_CANCELED) {
            finish()
            return@registerForActivityResult
        }
        lifecycleScope.launch {
            accountViewModel.updateUserFromBackend(application)
        }
    }

    private val navController: NavController
        get() {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
            return navHostFragment.navController
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.bottomNavigation.setupWithNavController(navController)

        lifecycleScope.launch {
            handleUser()
        }
    }

    private suspend fun handleUser() {
        when (val result = accountViewModel.findUser(application)) {
            is NetworkResponse.Success -> return
            is NetworkResponse.ServerError -> {
                logger.error(result.error) { "Server error" }
                showSnackbar(binding.root, R.string.server_error)
            }
            is NetworkResponse.NetworkError -> {
                logger.error(result.error) { "Network error" }
                showSnackbar(binding.root, R.string.network_error)
            }
            is NetworkResponse.UnknownError -> {
                logger.error(result.error) { "Application error" }
                showSnackbar(binding.root, R.string.application_error)
            }
        }

        val loginIntent = Intent(this, AuthActivity::class.java)
        launcher.launch(loginIntent)
    }
}
