package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart

import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Definition of the [Octopart REST API](https://octopart.com/api).
 */
interface OctopartAPI {
    /**
     * Search for the components by [query].
     * Use [start] and [limit] arguments for paging.
     */
    @GET(
        "v4/rest/parts/search" +
                "?include[]=specs" +
                "&include[]=short_description" +
                "&include[]=imagesets"
    )
    fun searchQuery(
        @Query("q") query: String,
        @Query("apikey") apiKey: String,
        @Query("start") start: Int,
        @Query("limit") limit: Int,
    ): Call<SearchResponse>
}
