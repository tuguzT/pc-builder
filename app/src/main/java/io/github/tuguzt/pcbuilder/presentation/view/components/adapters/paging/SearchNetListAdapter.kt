package io.github.tuguzt.pcbuilder.presentation.view.components.adapters.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import io.github.tuguzt.pcbuilder.databinding.ItemSearchNetComponentBinding
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.model.SearchResult
import io.github.tuguzt.pcbuilder.presentation.view.diffutils.DiffCallback
import io.github.tuguzt.pcbuilder.presentation.viewmodel.components.ComponentsSharedViewModel

/**
 * [PagingDataAdapter] subclass for searching results from the Octopart API.
 */
internal class SearchNetListAdapter(private val sharedViewModel: ComponentsSharedViewModel) :
    PagingDataAdapter<SearchResult, SearchResultViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemSearchNetComponentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return SearchResultViewHolder(binding, sharedViewModel)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
