package io.github.tuguzt.pcbuilder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import io.github.tuguzt.pcbuilder.databinding.ActivityMainBinding
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.github.tuguzt.pcbuilder.presentation.repository.findById
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentListFragmentDirections
import io.github.tuguzt.pcbuilder.presentation.view.snackbarShort

/**
 * Entry point of the application.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        RepositoryAccess.initRoom(application)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.bottomNavigation.setupWithNavController(navController)

        handleIntent()
    }

    private val navController: NavController
        get() {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
            return navHostFragment.navController
        }

    private fun handleIntent() {
        val parts = intent?.data?.toString()?.split("/") ?: return
        if ("components" in parts) {
            val id = parts.last()
            val liveData = RepositoryAccess.localRepository.findById(id, this)
            liveData.observe(this) {
                if (it != null) {
                    val component = when (it) {
                        is ComponentData -> it
                        else -> ComponentData(it)
                    }
                    val action = ComponentListFragmentDirections.actionComponentItemFragment(component)
                    navController.navigate(action)
                } else {
                    snackbarShort(binding.root) { "Such component does not exist!" }.show()
                }
                liveData.removeObservers(this)
            }
            return
        }
        if ("builds" in parts) {
            TODO("PC builds are not ready neither in Presentation, nor in Domain")
        }
        snackbarShort(binding.root) { "Provided deep link is invalid!" }.show()
    }
}
