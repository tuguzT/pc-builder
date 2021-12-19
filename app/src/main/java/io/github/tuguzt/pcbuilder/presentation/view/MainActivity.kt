package io.github.tuguzt.pcbuilder.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.ActivityMainBinding
import io.github.tuguzt.pcbuilder.presentation.view.account.LoginActivity
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountViewModel
import kotlinx.coroutines.launch
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

        lifecycleScope.launchWhenCreated {
            handleUser()
        }

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.bottomNavigation.setupWithNavController(navController)
    }

    private suspend fun handleUser() {
        if (accountViewModel.findUser(application) != null) return

        val loginIntent = Intent(this, LoginActivity::class.java)
        val contract = ActivityResultContracts.StartActivityForResult()
        registerForActivityResult(contract) {
            if (it.resultCode == RESULT_CANCELED) return@registerForActivityResult
            lifecycleScope.launch {
                accountViewModel.updateUserRemote(application)
                finish()
            }
        }.launch(loginIntent)
    }
}
