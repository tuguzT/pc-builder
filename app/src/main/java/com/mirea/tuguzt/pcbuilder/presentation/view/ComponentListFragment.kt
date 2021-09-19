package com.mirea.tuguzt.pcbuilder.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mirea.tuguzt.pcbuilder.databinding.FragmentComponentListBinding
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.domain.model.Size
import com.mirea.tuguzt.pcbuilder.presentation.view.adapters.ComponentListAdapter
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times

/**
 * A fragment representing a list of [components][Component].
 *
 * @see Component
 */
class ComponentListFragment : Fragment() {
    private var _binding: FragmentComponentListBinding? = null

    // This helper property is only valid between
    // `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = ComponentListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentListBinding.inflate(inflater, container, false)
        val view = binding.root

        view.adapter = ComponentListAdapter(listOf(
            object : Component {
                override val name = "Hello, World!"
                override val description = "Basic message from the beginner programmer"
                override val weight = 0 * grams
                override val size = Size(
                    length = 0 * meters,
                    width = 0 * meters,
                    height = 0 * meters,
                )
            }
        ))
        return view
    }
}
