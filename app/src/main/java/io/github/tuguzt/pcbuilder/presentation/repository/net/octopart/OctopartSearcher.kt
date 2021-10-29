package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendClient
import io.github.tuguzt.pcbuilder.presentation.repository.net.json
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.create

/**
 * Client of the Octopart REST API defined by [OctopartAPI].
 */
object OctopartSearcher {
    @JvmStatic
    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://octopart.com/api/")
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        .build()

    @JvmStatic
    private val octopartAPI: OctopartAPI = retrofit.create()

    @JvmStatic
    suspend fun searchComponents(query: String, start: Int, limit: Int): List<SearchResult> =
        octopartAPI.searchQuery(query, BackendClient.getOctopartToken(), start, limit)
            .await().results.map { SearchResult(it) }
}
