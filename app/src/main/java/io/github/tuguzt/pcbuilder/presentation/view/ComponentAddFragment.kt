@file:Suppress("NAME_SHADOWING")

package io.github.tuguzt.pcbuilder.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentAddBinding
import io.github.tuguzt.pcbuilder.domain.model.Component
import io.github.tuguzt.pcbuilder.domain.model.Size
import io.github.tuguzt.pcbuilder.presentation.viewmodel.ComponentAddViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MainActivityState
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MainActivityViewModel
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times

/**
 * A [Fragment] subclass for [Component] creation.
 *
 * @see Component
 */
class ComponentAddFragment : Fragment() {
    private val viewModel: ComponentAddViewModel by activityViewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentComponentAddBinding? = null

    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentAddBinding.inflate(inflater, container, false)

        activityViewModel.setActivityState(MainActivityState.FabVisibility(visible = false))

        val buttonAdd = binding.buttonAdd
        buttonAdd.setOnClickListener {
            val name = binding.name.text.toString()
            val description = binding.description.text.toString()
            val weight = binding.weight.text.toString()
            val length = binding.length.text.toString()
            val width = binding.width.text.toString()
            val height = binding.height.text.toString()

            if (name.isNotBlank() && description.isNotBlank() && weight.isNotBlank()
                && length.isNotBlank() && width.isNotBlank() && height.isNotBlank()
            ) {
                try {
                    val weight = weight.toDouble() * grams
                    val size = Size(
                        length.toDouble() * meters,
                        width.toDouble() * meters,
                        height.toDouble() * meters,
                    )
                    viewModel.addComponent(name, description, weight, size)

                    val fragmentManager = requireActivity().supportFragmentManager
                    val navHostFragment =
                        fragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    navHostFragment.navController.popBackStack()

                    activityViewModel.setActivityState(MainActivityState.FabVisibility(visible = true))

                    snackbarShort { "Component was successfully added" }
                } catch (e: NumberFormatException) {
                    snackbarShort { "Incorrect input!" }
                }
            } else {
                snackbarShort { "Some fields are empty!" }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
