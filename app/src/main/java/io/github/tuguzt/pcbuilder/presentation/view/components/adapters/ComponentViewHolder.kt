package io.github.tuguzt.pcbuilder.presentation.view.components.adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.databinding.ItemComponentBinding
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentListFragmentDirections
import java.io.FileNotFoundException

class ComponentViewHolder(private val binding: ItemComponentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var _component: ComponentData
    val component get() = _component

    init {
        binding.root.setOnClickListener {
            val component = ComponentData(component)
            val action = ComponentListFragmentDirections.actionComponentItemFragment(component)
            it.findNavController().navigate(action)
        }
    }

    fun bind(component: ComponentData) {
        _component = component
        binding.run {
            Log.e(this::class.simpleName, component.imageUri.toString())
            component.imageUri?.let {
                try {
                    val uri = Uri.parse(it)
                    val contentResolver = binding.root.context.contentResolver
                    imageView.setImageBitmap(
                        BitmapFactory.decodeFileDescriptor(
                            contentResolver.openFileDescriptor(uri, "r")?.fileDescriptor
                        )
                    )
                } catch (e: FileNotFoundException) {
                    Log.e(this::class.simpleName, "File not found: $e")
                } catch (e: NullPointerException) {
                    Log.e(this::class.simpleName, "WTF...: $e")
                }
            }
            name.text = component.name
            description.text = component.description
        }
    }
}
