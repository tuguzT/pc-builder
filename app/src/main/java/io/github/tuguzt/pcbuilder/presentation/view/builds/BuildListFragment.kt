package io.github.tuguzt.pcbuilder.presentation.view.builds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentBuildListBinding
import io.github.tuguzt.pcbuilder.presentation.view.MainActivity

class BuildListFragment : Fragment() {
    private var _binding: FragmentBuildListBinding? = null

    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private inline val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBuildListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity() as MainActivity

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.builds_fragment))
        val navController = findNavController()
        activity.binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
