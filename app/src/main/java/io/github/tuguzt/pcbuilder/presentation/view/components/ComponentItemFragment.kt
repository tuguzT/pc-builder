package io.github.tuguzt.pcbuilder.presentation.view.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentItemBinding

class ComponentItemFragment : Fragment() {
    private val args: ComponentItemFragmentArgs by navArgs()

    private var _binding: FragmentComponentItemBinding? = null

    // This helper property is only valid between
    // `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentItemBinding.inflate(inflater, container, false)

        val component = args.component
        binding.run {
            name.text = getString(R.string.component_name, component.name)
            description.text = getString(R.string.component_description, component.description)
            weight.text = getString(R.string.component_weight, component.weight)
            length.text = getString(R.string.component_length, component.size.length)
            width.text = getString(R.string.component_width, component.size.width)
            height.text = getString(R.string.component_height, component.size.height)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
