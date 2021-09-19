@file:Suppress("NAME_SHADOWING")

package com.mirea.tuguzt.pcbuilder.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.mirea.tuguzt.pcbuilder.MainActivity
import com.mirea.tuguzt.pcbuilder.R
import com.mirea.tuguzt.pcbuilder.databinding.FragmentComponentAddBinding
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.domain.model.Size
import com.mirea.tuguzt.pcbuilder.presentation.viewmodel.ComponentAddViewModel
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times

/**
 * A [Fragment] subclass for [Component] creation.
 *
 * @see Component
 */
class ComponentAddFragment : Fragment() {
    private var _binding: FragmentComponentAddBinding? = null
    private var _viewModel: ComponentAddViewModel? = null

    // This helper properties are only valid between
    // `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.onBackPressedDispatcher.addCallback {
            activity.binding.fab.show()

            val fragmentManager = activity.supportFragmentManager
            val navHostFragment =
                fragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navHostFragment.navController.popBackStack()

            remove()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentAddBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[ComponentAddViewModel::class.java]

        val activity = requireActivity() as MainActivity
        activity.binding.fab.hide()

        val buttonAdd = binding.buttonAdd
        buttonAdd.setOnClickListener {
            val name = binding.name.text.toString()
            val description = binding.description.text.toString()
            val weight = binding.weight.text.toString()
            val length = binding.length.text.toString()
            val width = binding.width.text.toString()
            val height = binding.height.text.toString()
            if (name.isNotEmpty() && description.isNotEmpty() && weight.isNotEmpty()
                && length.isNotEmpty() && width.isNotEmpty() && height.isNotEmpty()
            ) {
                val weight = weight.toDouble() * grams
                val size = Size(
                    length.toDouble() * meters,
                    width.toDouble() * meters,
                    height.toDouble() * meters,
                )
                viewModel.addComponent(name, description, weight, size)

                val activityBinding = activity.binding
                activityBinding.fab.show()

                val fragmentManager = activity.supportFragmentManager
                val navHostFragment =
                    fragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navHostFragment.navController.popBackStack()
            } else {
                Snackbar.make(binding.root, "Some fields are empty!", Snackbar.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
    }
}
