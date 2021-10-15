package io.github.tuguzt.pcbuilder.presentation.view.components.adapters.paging

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.tuguzt.pcbuilder.databinding.ItemComponentBinding
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.SearchResult

internal class SearchResultViewHolder(
    private val binding: ItemComponentBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var _searchResult: SearchResult
    val searchResult get() = _searchResult

    fun bind(searchResult: SearchResult) {
        _searchResult = searchResult
        binding.run {
            name.visibility = View.GONE
            description.text = searchResult.description
            val image = searchResult.images.firstOrNull()
            val url = when {
                image?.swatchImage != null -> image.swatchImage.url
                image?.smallImage != null -> image.smallImage.url
                image?.mediumImage != null -> image.mediumImage.url
                image?.largeImage != null -> image.largeImage.url
                else -> return@run
            }
            Picasso.get()
                .load(url)
                .into(imageView)
            imageView.visibility = View.VISIBLE
        }
    }
}
