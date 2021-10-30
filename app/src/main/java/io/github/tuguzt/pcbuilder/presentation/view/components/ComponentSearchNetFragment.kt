package io.github.tuguzt.pcbuilder.presentation.view.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentSearchNetBinding
import io.github.tuguzt.pcbuilder.presentation.view.components.adapters.paging.SearchNetListAdapter
import io.github.tuguzt.pcbuilder.presentation.view.decorations.MarginDecoration
import io.github.tuguzt.pcbuilder.presentation.view.toastShort
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentSearchNetViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel

/**
 * A [Fragment] subclass to search components by REST API.
 */
class ComponentSearchNetFragment : Fragment() {
    private val sharedViewModel: ComponentsSharedViewModel by navGraphViewModels(R.id.components_nav_graph)
    private val viewModel: ComponentSearchNetViewModel by viewModels()

    private var _binding: FragmentComponentSearchNetBinding? = null
    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private inline val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentSearchNetBinding.inflate(inflater, container, false)

        val pagingAdapter = SearchNetListAdapter(sharedViewModel)
        binding.list.adapter = pagingAdapter

        val spaceSize = resources.getDimensionPixelSize(R.dimen.list_item_margin)
        binding.list.addItemDecoration(MarginDecoration(spaceSize))

        binding.searchView.run {
            isSubmitButtonEnabled = true
            setIconifiedByDefault(false)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?) = true

                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query.isNullOrBlank()) {
                        toastShort { "Query cannot be empty!" }.show()
                        return true
                    }
                    viewModel.searchComponents(query.trim(), 3).observe(viewLifecycleOwner) {
                        pagingAdapter.submitData(lifecycle, it)
                    }
                    return true
                }
            })
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
