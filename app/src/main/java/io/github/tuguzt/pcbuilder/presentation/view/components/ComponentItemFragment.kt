package io.github.tuguzt.pcbuilder.presentation.view.components

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentItemBinding
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.view.hasOptionsMenu
import io.github.tuguzt.pcbuilder.presentation.view.snackbarShort
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentListViewModel

class ComponentItemFragment : Fragment() {
    private val args: ComponentItemFragmentArgs by navArgs()
    private val viewModel: ComponentListViewModel by viewModels()

    private var _binding: FragmentComponentItemBinding? = null

    // This helper property is only valid between
    // `onCreateView` and `onDestroyView`.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComponentItemBinding.inflate(inflater, container, false)
        hasOptionsMenu = true

        val component = args.component
        binding.run {
            name.text = getString(R.string.component_name, component.name)
            description.text = getString(R.string.component_description, component.description)
            weight.text = getString(R.string.component_weight, component.weight)
            length.text = getString(R.string.component_length, component.size.length)
            width.text = getString(R.string.component_width, component.size.width)
            height.text = getString(R.string.component_height, component.size.height)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.component_item_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.component_item_share -> {
            val component = args.component
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                val link = "https://android.pcbuilder/components/${component.id}"
                putExtra(Intent.EXTRA_TEXT, link)
                type = "text/plain"
            }
            val title = getString(R.string.item_component_share_title)
            val shareIntent = Intent.createChooser(sendIntent, title)
            startActivity(shareIntent)
            true
        }
        R.id.component_item_image -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val activity = requireActivity()
                activity.activityResultRegistry
                    .register("key", ActivityResultContracts.OpenDocument()) { uri ->
                        activity.applicationContext.contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION,
                        )
                        val component = args.component
                        viewModel.updateComponent(ComponentData(
                            component.id,
                            component.name,
                            component.description,
                            component.weight,
                            component.size,
                            uri.toString(),
                        ))
                    }
                    .launch(arrayOf("image/*"))
                true
            } else {
                snackbarShort { "This is supported only on Android devices above 4.4 KitKat" }.show()
                super.onOptionsItemSelected(item)
            }
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
