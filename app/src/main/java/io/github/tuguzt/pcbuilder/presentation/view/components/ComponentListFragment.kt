package io.github.tuguzt.pcbuilder.presentation.view.components

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentListBinding
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.view.components.adapters.ComponentListAdapter
import io.github.tuguzt.pcbuilder.presentation.view.decorations.MarginDecoration
import io.github.tuguzt.pcbuilder.presentation.view.hasOptionsMenu
import io.github.tuguzt.pcbuilder.presentation.view.snackbarShort
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel

/**
 * A [Fragment] subclass which represents list of [components][Component].
 *
 * @see Component
 */
class ComponentListFragment : Fragment() {
    private val sharedViewModel: ComponentsSharedViewModel by navGraphViewModels(R.id.main_nav_graph)

    private var _binding: FragmentComponentListBinding? = null
    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentListBinding.inflate(inflater, container, false)
        hasOptionsMenu = true

        val adapter = ComponentListAdapter(sharedViewModel)
        binding.list.adapter = adapter

        val spaceSize = resources.getDimensionPixelSize(R.dimen.list_item_margin)
        binding.list.addItemDecoration(MarginDecoration(spaceSize))

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT,
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val item = adapter.currentList[position]

                sharedViewModel.deleteComponent(item)
                snackbarShort { "Component was successfully deleted" }.show()
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.list)

        binding.fab.setOnClickListener {
            val action = ComponentListFragmentDirections.actionComponentAddFragment()
            findNavController().navigate(action)
        }

        sharedViewModel.allComponents.observe(viewLifecycleOwner) {
            it?.let { adapter.submitList(it) }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_components_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.toolbar_components_search -> {
            val action = ComponentListFragmentDirections.actionComponentSearchNetFragment()
            findNavController().navigate(action)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
