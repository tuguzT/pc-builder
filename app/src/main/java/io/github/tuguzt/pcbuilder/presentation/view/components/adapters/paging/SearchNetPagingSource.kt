package io.github.tuguzt.pcbuilder.presentation.view.components.adapters.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendOctopartAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.model.SearchResult
import retrofit2.await

internal class SearchNetPagingSource(
    private val query: String,
    private val pageSize: Int,
    private val octopartAPI: BackendOctopartAPI,
) : PagingSource<Int, SearchResult>() {

    override fun getRefreshKey(state: PagingState<Int, SearchResult>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResult> {
        val position = params.key ?: 0
        val offset = if (params.key != null) (position * pageSize) else 0
        return try {
            val data = octopartAPI.search(query, offset, pageSize).await()
            val nextKey = if (data.isEmpty()) null else position + 1
            LoadResult.Page(data, prevKey = null, nextKey)
        } catch (exception: Exception) {
            Log.e("MY_APP", "ERROR", exception)
            LoadResult.Error(exception)
        }
    }
}
