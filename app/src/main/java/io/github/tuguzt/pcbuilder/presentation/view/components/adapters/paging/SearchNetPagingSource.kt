package io.github.tuguzt.pcbuilder.presentation.view.components.adapters.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.OctopartSearcher
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.SearchResult

internal class SearchNetPagingSource(
    private val query: String,
    private val pageSize: Int,
) : PagingSource<Int, SearchResult>() {

    override fun getRefreshKey(state: PagingState<Int, SearchResult>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResult> {
        val position = params.key ?: 0
        val offset = if (params.key != null) (position * pageSize) else 0
        return try {
            val data = OctopartSearcher.searchComponentsSuspend(query, offset, pageSize)
            val nextKey = if (data.isEmpty()) null else position + 1
            LoadResult.Page(data, prevKey = null, nextKey)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}
