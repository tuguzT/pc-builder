package io.github.tuguzt.pcbuilder.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentBinding

class ComponentFragment : Fragment() {
    private val args: ComponentFragmentArgs by navArgs()

    private var _binding: FragmentComponentBinding? = null

    // This helper property is only valid between
    // `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentBinding.inflate(inflater, container, false)

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
