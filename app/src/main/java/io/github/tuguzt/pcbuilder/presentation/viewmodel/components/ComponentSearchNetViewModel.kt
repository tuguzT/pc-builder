package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.OctopartAPI
import io.github.tuguzt.pcbuilder.presentation.view.components.adapters.paging.SearchNetPagingSource
import io.github.tuguzt.pcbuilder.presentation.view.components.ComponentSearchNetFragment

/**
 * View model for [ComponentSearchNetFragment].
 */
class ComponentSearchNetViewModel(
    private val octopartAPI: OctopartAPI,
    private val backendAPI: BackendAPI,
) : ViewModel() {
    fun searchComponents(query: String, pageSize: Int) = Pager(
        PagingConfig(pageSize, enablePlaceholders = false),
        pagingSourceFactory = { SearchNetPagingSource(query, pageSize, octopartAPI, backendAPI) }
    ).liveData.cachedIn(viewModelScope)
}
