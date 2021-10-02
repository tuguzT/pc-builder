package io.github.tuguzt.pcbuilder.presentation.view.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentAddBinding
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.presentation.view.snackbarShort
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentAddViewModel
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

    private var _binding: FragmentComponentAddBinding? = null

    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentAddBinding.inflate(inflater, container, false)

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
                    @Suppress("NAME_SHADOWING")
                    val weight = weight.toDouble() * grams
                    val size = Size(
                        length.toDouble() * meters,
                        width.toDouble() * meters,
                        height.toDouble() * meters,
                    )
                    viewModel.addComponent(name, description, weight, size)

                    val fragmentManager = requireActivity().supportFragmentManager
                    val navHostFragment =
                        fragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
                    navHostFragment.navController.popBackStack()

                    snackbarShort { "Component was successfully added" }.show()
                } catch (e: NumberFormatException) {
                    snackbarShort { "Incorrect input!" }.show()
                }
            } else {
                snackbarShort { "Some fields are empty!" }.show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
