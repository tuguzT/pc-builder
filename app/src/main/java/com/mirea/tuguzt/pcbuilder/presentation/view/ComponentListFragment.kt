package com.mirea.tuguzt.pcbuilder.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mirea.tuguzt.pcbuilder.MainActivity
import com.mirea.tuguzt.pcbuilder.databinding.FragmentComponentListBinding
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.presentation.view.adapters.ComponentListAdapter
import com.mirea.tuguzt.pcbuilder.presentation.viewmodel.ComponentListViewModel

/**
 * A [Fragment] subclass which represents list of [components][Component].
 *
 * @see Component
 */
class ComponentListFragment : Fragment() {
    private val viewModel: ComponentListViewModel by activityViewModels()

    private var _binding: FragmentComponentListBinding? = null
    // This helper property is only valid between
    // `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentListBinding.inflate(inflater, container, false)

        val view = binding.root
        val activity = requireActivity() as MainActivity
        activity.binding.fab.show()

        val adapter = ComponentListAdapter()
        view.adapter = adapter

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
                viewModel.deleteComponent(adapter.currentList[position])

                snackbarShort { "Component was successfully deleted" }
            }
        })
        itemTouchHelper.attachToRecyclerView(view)

        viewModel.getAllComponents().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
