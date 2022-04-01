package io.github.tuguzt.pcbuilder.presentation.view.components

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentBinding
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.model.component.ComponentData
import io.github.tuguzt.pcbuilder.presentation.view.hasOptionsMenu
import io.github.tuguzt.pcbuilder.presentation.view.popBackStackRoot
import io.github.tuguzt.pcbuilder.presentation.view.toastShort
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentViewModel
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * A [Fragment] subclass which represents specs of PC [component][Component].
 *
 * @see Component
 */
class ComponentFragment : Fragment() {
    private val args: ComponentFragmentArgs by navArgs()

    private val sharedViewModel: ComponentsSharedViewModel by koinNavGraphViewModel(R.id.components_nav_graph)
    private val viewModel: ComponentViewModel by viewModel { parametersOf(args.id) }

    private var _binding: FragmentComponentBinding? = null

    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private inline val binding get() = _binding!!

    private lateinit var _getImageUri: ActivityResultLauncher<Array<String>>
    private inline val getImageUri get() = _getImageUri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _getImageUri = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            it?.let { uri ->
                val contentResolver = requireActivity().applicationContext.contentResolver
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION,
                )
                val component = viewModel.component.value!!
                val updatedComponent = ComponentData(
                    component.id,
                    component.name,
                    component.description,
                    component.weight,
                    component.size,
                    uri.toString(),
                )
                sharedViewModel.updateComponent(updatedComponent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        hasOptionsMenu = true
        return FragmentComponentBinding.inflate(inflater, container, false)
            .also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.component.observe(viewLifecycleOwner) { component: Component? ->
            if (component == null) {
                toastShort { "Component does not exist!" }.show()
                popBackStackRoot()
                return@observe
            }
            binding.run {
                name.text = getString(R.string.component_name, component.name)
                description.text = getString(R.string.component_description, component.description)
                weight.text = getString(R.string.component_weight, component.weight)
                length.text = getString(R.string.component_length, component.size.length)
                width.text = getString(R.string.component_width, component.size.width)
                height.text = getString(R.string.component_height, component.size.height)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_component_item_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.toolbar_component_item_share -> {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                val link = "https://tuguzt.github.io/pcbuilder/components/${args.id}"
                putExtra(Intent.EXTRA_TEXT, link)
                type = "text/plain"
            }
            val title = getString(R.string.item_component_share_title)
            val shareIntent = Intent.createChooser(sendIntent, title)
            startActivity(shareIntent)
            true
        }
        R.id.toolbar_component_item_image -> {
            getImageUri.launch(arrayOf("image/*"))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
