package io.github.tuguzt.pcbuilder.presentation.view.components

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentListBinding
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.presentation.view.components.adapters.ComponentListAdapter
import io.github.tuguzt.pcbuilder.presentation.view.decorations.MarginDecoration
import io.github.tuguzt.pcbuilder.presentation.view.hasOptionsMenu
import io.github.tuguzt.pcbuilder.presentation.view.snackbarShort
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

/**
 * A [Fragment] subclass which represents list of [components][Component].
 *
 * @see Component
 */
class ComponentListFragment : Fragment() {
    private val accountViewModel: AccountViewModel by koinNavGraphViewModel(R.id.main_nav_graph)
    private val sharedViewModel: ComponentsSharedViewModel by koinNavGraphViewModel(R.id.components_nav_graph)

    private var _binding: FragmentComponentListBinding? = null

    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private inline val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        hasOptionsMenu = true
        return FragmentComponentListBinding.inflate(inflater, container, false)
            .also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                snackbarShort { getString(R.string.component_deleted) }.show()
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_components_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val user = accountViewModel.currentUser ?: return
        val task = menu.findItem(R.id.toolbar_components_task)!!
        task.isVisible = user.role != UserRole.User
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
