package io.github.tuguzt.pcbuilder.presentation.view.builds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.tuguzt.pcbuilder.databinding.FragmentBuildListBinding

class BuildListFragment : Fragment() {
    private var _binding: FragmentBuildListBinding? = null

    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private inline val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentBuildListBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
