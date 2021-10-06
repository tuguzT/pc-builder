package io.github.tuguzt.pcbuilder.presentation.view.components.adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.databinding.ItemComponentBinding
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentListFragmentDirections
import java.io.FileNotFoundException

class ComponentViewHolder(private val binding: ItemComponentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        private val LOG_TAG = ComponentViewHolder::class.simpleName
    }

    private lateinit var _component: ComponentData
    val component get() = _component

    init {
        binding.root.setOnClickListener {
            val action = ComponentListFragmentDirections.actionComponentItemFragment(component.id)
            it.findNavController().navigate(action)
        }
    }

    fun bind(component: ComponentData) {
        _component = component
        binding.run {
            name.text = component.name
            description.text = component.description

            val imageUri = component.imageUri
            if (imageUri == null) {
                imageView.visibility = View.GONE
                return@run
            }
            imageView.visibility = View.VISIBLE
            try {
                val uri = Uri.parse(imageUri)
                val contentResolver = binding.root.context.contentResolver
                imageView.setImageBitmap(
                    BitmapFactory.decodeFileDescriptor(
                        contentResolver.openFileDescriptor(uri, "r")?.fileDescriptor
                    )
                )
            } catch (e: FileNotFoundException) {
                Log.e(LOG_TAG, "File not found: $e")
            } catch (e: NullPointerException) {
                Log.e(LOG_TAG, "WTF...: $e")
            }
        }
    }
}
