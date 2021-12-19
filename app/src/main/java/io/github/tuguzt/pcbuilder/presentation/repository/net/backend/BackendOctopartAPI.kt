package io.github.tuguzt.pcbuilder.presentation.repository.net.backend

import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Definition of the REST API for the remote `PC Builder` backend server.
 */
interface BackendOctopartAPI {
    @GET("search")
    fun search(
        @Query("query") query: String,
        @Query("start") start: Int,
        @Query("limit") limit: Int,
    ): Call<List<SearchResult>>
}
