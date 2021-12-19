package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendOctopartAPI
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentSearchNetFragment
import io.github.tuguzt.pcbuilder.presentation.view.components.adapters.paging.SearchNetPagingSource

/**
 * View model for [ComponentSearchNetFragment].
 */
class ComponentSearchNetViewModel(private val octopartAPI: BackendOctopartAPI) : ViewModel() {
    fun searchComponents(query: String, pageSize: Int) = Pager(
        PagingConfig(pageSize, enablePlaceholders = false),
        pagingSourceFactory = { SearchNetPagingSource(query, pageSize, octopartAPI) }
    ).liveData.cachedIn(viewModelScope)
}
