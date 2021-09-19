package com.mirea.tuguzt.pcbuilder.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mirea.tuguzt.pcbuilder.databinding.FragmentComponentListBinding
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.presentation.view.adapters.ComponentListAdapter
import com.mirea.tuguzt.pcbuilder.presentation.viewmodel.ComponentListViewModel

/**
 * A fragment representing a list of [components][Component].
 *
 * @see Component
 */
class ComponentListFragment : Fragment() {
    private var _binding: FragmentComponentListBinding? = null
    private var _viewModel: ComponentListViewModel? = null

    // This helper properties are only valid between
    // `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentListBinding.inflate(inflater, container, false)
        val view = binding.root

        _viewModel = ViewModelProvider(this)[ComponentListViewModel::class.java]
        viewModel.getAllComponents().observe(viewLifecycleOwner) {
            view.adapter = ComponentListAdapter(it)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
    }
}
