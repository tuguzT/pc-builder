package io.github.tuguzt.pcbuilder.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import io.github.tuguzt.pcbuilder.presentation.view.components.adapters.paging.SearchNetPagingSource

internal class ComponentSearchNetViewModel : ViewModel() {
    fun searchComponents(query: String, pageSize: Int) = Pager(
        PagingConfig(pageSize, enablePlaceholders = false),
        pagingSourceFactory = { SearchNetPagingSource(query, pageSize) }
    ).liveData.cachedIn(viewModelScope)
}
