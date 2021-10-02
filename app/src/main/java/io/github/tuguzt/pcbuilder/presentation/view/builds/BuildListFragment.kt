package io.github.tuguzt.pcbuilder.presentation.view.builds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import io.github.tuguzt.pcbuilder.databinding.FragmentBuildListBinding
import io.github.tuguzt.pcbuilder.presentation.viewmodel.builds.BuildListViewModel

class BuildListFragment : Fragment() {
    private val viewModel: BuildListViewModel by activityViewModels()

    private var _binding: FragmentBuildListBinding? = null

    // This helper property is only valid between
    // `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBuildListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
