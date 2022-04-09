package io.github.tuguzt.pcbuilder.presentation.view.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentAddBinding
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.presentation.view.showSnackbar
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import org.koin.androidx.navigation.koinNavGraphViewModel

/**
 * A [Fragment] subclass for [Component] creation.
 *
 * @see Component
 */
class ComponentAddFragment : Fragment() {
    private val sharedViewModel: ComponentsSharedViewModel by koinNavGraphViewModel(R.id.components_nav_graph)

    private var _binding: FragmentComponentAddBinding? = null

    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private inline val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                    sharedViewModel.addComponent(name.trim(), description.trim(), weight, size)

                    val fragmentManager = requireActivity().supportFragmentManager
                    val navHostFragment =
                        fragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
                    navHostFragment.navController.popBackStack()

                    showSnackbar(binding.root, R.string.component_added)
                } catch (e: NumberFormatException) {
                    showSnackbar(binding.root, R.string.incorrect_input)
                }
            } else {
                showSnackbar(binding.root, R.string.some_fields_empty)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
