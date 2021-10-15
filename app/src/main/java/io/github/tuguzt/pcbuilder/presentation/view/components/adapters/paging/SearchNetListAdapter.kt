package io.github.tuguzt.pcbuilder.presentation.view.components.adapters.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import io.github.tuguzt.pcbuilder.databinding.ItemComponentBinding
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.SearchResult
import io.github.tuguzt.pcbuilder.presentation.view.diffutils.DiffCallback

/**
 * [PagingDataAdapter] subclass for searching results from the Octopart API.
 */
internal class SearchNetListAdapter :
    PagingDataAdapter<SearchResult, SearchResultViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemComponentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return SearchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
