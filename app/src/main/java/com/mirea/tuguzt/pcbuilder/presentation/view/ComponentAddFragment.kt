package com.mirea.tuguzt.pcbuilder.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.mirea.tuguzt.pcbuilder.MainActivity
import com.mirea.tuguzt.pcbuilder.R
import com.mirea.tuguzt.pcbuilder.databinding.FragmentComponentAddBinding
import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.domain.model.Size
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

/**
 * A [Fragment] subclass for [Component] creation.
 * Use the [ComponentAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * @see Component
 */
class ComponentAddFragment : Fragment() {
    private var _binding: FragmentComponentAddBinding? = null

    // This helper property is only valid between
    // `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    private var componentName: String? = null
    private var componentDescription: String? = null
    private var componentWeight: Measure<Mass>? = null
    private var componentSize: Size? = null

    companion object {
        private const val ARG_COMPONENT_NAME =
            "com.mirea.tuguzt.pcbuilder.presentation.view.ComponentAddFragment.componentName"
        private const val ARG_COMPONENT_DESCRIPTION =
            "com.mirea.tuguzt.pcbuilder.presentation.view.ComponentAddFragment.componentDescription"
        private const val ARG_COMPONENT_WEIGHT =
            "com.mirea.tuguzt.pcbuilder.presentation.view.ComponentAddFragment.componentWeight"
        private const val ARG_COMPONENT_LENGTH =
            "com.mirea.tuguzt.pcbuilder.presentation.view.ComponentAddFragment.componentLength"
        private const val ARG_COMPONENT_WIDTH =
            "com.mirea.tuguzt.pcbuilder.presentation.view.ComponentAddFragment.componentWidth"
        private const val ARG_COMPONENT_HEIGHT =
            "com.mirea.tuguzt.pcbuilder.presentation.view.ComponentAddFragment.componentHeight"

        @JvmStatic
        fun newInstance() = ComponentAddFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            componentName = it.getString(ARG_COMPONENT_NAME)
            componentDescription = it.getString(ARG_COMPONENT_DESCRIPTION)
            componentWeight = it.getDouble(ARG_COMPONENT_WEIGHT) * grams
            componentSize = Size(
                length = it.getDouble(ARG_COMPONENT_LENGTH) * meters,
                width = it.getDouble(ARG_COMPONENT_WIDTH) * meters,
                height = it.getDouble(ARG_COMPONENT_HEIGHT) * meters,
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentAddBinding.inflate(inflater, container, false)

        val activity = requireActivity() as MainActivity
        val activityBinding = activity.binding

        val buttonAdd = binding.buttonAdd
        buttonAdd.setOnClickListener {
            val fragmentManager = activity.supportFragmentManager
            val navHostFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navHostFragment.navController.navigate(R.id.action_component_list_fragment)

            activityBinding.fab.show()
        }

        return binding.root
    }
}
