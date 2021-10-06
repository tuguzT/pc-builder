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
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel
import java.io.FileNotFoundException

class ComponentViewHolder(
    private val binding: ItemComponentBinding,
    private val sharedViewModel: ComponentsSharedViewModel,
) : RecyclerView.ViewHolder(binding.root) {

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
            } catch (e: SecurityException) {
                Log.e(LOG_TAG, "Cannot obtain an image due to security error", e)
                sharedViewModel.updateComponent(ComponentData(component))
                imageView.visibility = View.GONE
            } catch (e: FileNotFoundException) {
                Log.e(LOG_TAG, "Image not found", e)
                sharedViewModel.updateComponent(ComponentData(component))
                imageView.visibility = View.GONE
            } catch (e: NullPointerException) {
                Log.e(LOG_TAG, "Content provider recently crashed", e)
                sharedViewModel.updateComponent(ComponentData(component))
                imageView.visibility = View.GONE
            }
        }
    }
}
