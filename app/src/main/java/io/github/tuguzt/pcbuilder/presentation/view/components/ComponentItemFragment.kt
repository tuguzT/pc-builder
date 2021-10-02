package io.github.tuguzt.pcbuilder.presentation.view.components

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentComponentItemBinding
import io.github.tuguzt.pcbuilder.presentation.view.hasOptionsMenu

class ComponentItemFragment : Fragment() {
    private val args: ComponentItemFragmentArgs by navArgs()

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
            val title = getString(R.string.component_item_share_title)
            val shareIntent = Intent.createChooser(sendIntent, title)
            startActivity(shareIntent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
