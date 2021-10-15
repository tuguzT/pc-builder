package io.github.tuguzt.pcbuilder.presentation.view.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentSearchNetBinding

class ComponentSearchNetFragment : Fragment() {
    private var _binding: FragmentComponentSearchNetBinding? = null
    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentSearchNetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
