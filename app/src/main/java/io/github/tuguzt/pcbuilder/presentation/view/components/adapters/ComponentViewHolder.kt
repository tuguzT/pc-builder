package io.github.tuguzt.pcbuilder.presentation.view.components.adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import io.github.tuguzt.pcbuilder.databinding.ItemComponentBinding
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.model.ComponentData
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentListFragmentDirections
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel
import kotlinx.coroutines.*
import java.io.FileNotFoundException

/**
 * [RecyclerView.ViewHolder] subclass for [component][Component].
 *
 * @see Component
 */
class ComponentViewHolder(
    private val binding: ItemComponentBinding,
    private val sharedViewModel: ComponentsSharedViewModel,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private val LOG_TAG = ComponentViewHolder::class.simpleName
    }

    private lateinit var _component: ComponentData
    private inline val component get() = _component

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

            val imageUri = component.imageUri ?: return
            imageView.visibility = View.VISIBLE
            val uri = Uri.parse(imageUri)
            val contentResolver = binding.root.context.contentResolver

            val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                val message = when (throwable) {
                    is SecurityException -> "Cannot obtain an image due to security error"
                    is FileNotFoundException -> "Image not found"
                    is NullPointerException -> "Content provider recently crashed"
                    else -> "Unknown error"
                }
                Log.e(LOG_TAG, message, throwable)
                sharedViewModel.updateComponent(ComponentData(component))
                CoroutineScope(Dispatchers.Main).launch {
                    imageView.visibility = View.GONE
                }
            }
            CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
                val fileDescriptor = contentResolver?.openFileDescriptor(uri, "r")?.fileDescriptor
                val bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                withContext(Dispatchers.Main) {
                    imageView.setImageBitmap(bitmap)
                }
            }
        }
    }
}
