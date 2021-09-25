package io.github.tuguzt.pcbuilder

import android.os.Bundle
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import io.github.tuguzt.pcbuilder.databinding.ActivityMainBinding
import io.github.tuguzt.pcbuilder.presentation.repository.RepositoryAccess
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MainActivityState
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MainActivityViewModel

/**
 * Entry point of the application.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        RepositoryAccess.initRoom(application)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener(fabOnClickListener)

        viewModel.activityState.observe(this, this::handleState)
    }

    private val fabOnClickListener = OnClickListener {
        val fragmentContainer = binding.navHostFragment
        fragmentContainer.findNavController().navigate(R.id.action_component_add_fragment)
    }

    private fun handleState(state: MainActivityState): Unit = when (state) {
        is MainActivityState.FabVisibility -> {
            val fab = binding.fab
            val visible = state.visible

            fab.run {
                if (visible) {
                    show()
                    setOnClickListener(fabOnClickListener)
                } else {
                    hide()
                    setOnClickListener(null)
                }
                isClickable = visible
            }
        }
    }
}
