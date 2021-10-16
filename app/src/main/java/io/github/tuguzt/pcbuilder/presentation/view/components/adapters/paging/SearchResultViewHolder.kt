package io.github.tuguzt.pcbuilder.presentation.view.components.adapters.paging

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.DialogAddSearchNetComponentBinding
import io.github.tuguzt.pcbuilder.databinding.ItemSearchNetComponentBinding
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.SearchResult
import io.github.tuguzt.pcbuilder.presentation.view.saveImage
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import java.lang.Exception

internal class SearchResultViewHolder(
    private val binding: ItemSearchNetComponentBinding,
    private val sharedViewModel: ComponentsSharedViewModel,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private val LOG_TAG = SearchResultViewHolder::class.simpleName
    }

    private var imageBitmap: Bitmap? = null

    private lateinit var _searchResult: SearchResult
    val searchResult get() = _searchResult

    init {
        binding.saveLocalStorage.setOnClickListener {
            val context = binding.root.context
            AlertDialog.Builder(context).run {
                val dialogBinding = DialogAddSearchNetComponentBinding
                    .inflate(LayoutInflater.from(context))
                setView(dialogBinding.root)
                setTitle(R.string.add_new_component)

                setPositiveButton(R.string.add) { _, _ ->
                    val name = dialogBinding.editText.text.toString()
                    if (name.isNotBlank()) {
                        val imageUri = imageBitmap?.let { saveImage(it, context)?.toString() }
                        sharedViewModel.addComponent(
                            name.trim(),
                            searchResult.description,
                            0 * grams,
                            Size(length = 0 * meters, width = 0 * meters, height = 0 * meters),
                            imageUri,
                        )
                        binding.saveLocalStorage.run {
                            isClickable = false
                            visibility = View.GONE
                        }
                    }
                }

                show()
            }
        }
    }

    fun bind(searchResult: SearchResult) {
        _searchResult = searchResult
        binding.run {
            description.text = searchResult.description
            val image = searchResult.images.firstOrNull()
            val imageUrl = when {
                image?.swatchImage != null -> image.swatchImage.url
                image?.smallImage != null -> image.smallImage.url
                image?.mediumImage != null -> image.mediumImage.url
                image?.largeImage != null -> image.largeImage.url
                else -> return@run
            }
            Picasso.get()
                .load(imageUrl)
                .into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                        imageBitmap = bitmap
                        imageView.visibility = View.VISIBLE
                        imageView.setImageBitmap(bitmap)
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        Log.e(LOG_TAG, "Bitmap loading failed", e)
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) = Unit
                })
        }
    }
}
