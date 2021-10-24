package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model.SearchResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Client of the Octopart REST API defined by [OctopartAPI].
 */
internal object OctopartSearcher {
    @JvmStatic
    private val LOG_TAG = OctopartSearcher::class.simpleName

    @JvmStatic
    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://octopart.com/api/")
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        .build()

    @JvmStatic
    private val octopartAPI: OctopartAPI = retrofit.create(OctopartAPI::class.java)

    @JvmStatic
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun searchComponentsSuspend(query: String, start: Int, limit: Int): List<SearchResult> {
        return suspendCancellableCoroutine { continuation ->
            octopartAPI.searchQuery(query, "8f0447c8-f2c0-483b-bedd-144fed83bcce", start, limit)
                .enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>,
                    ) {
                        if (response.isSuccessful) {
                            val searchResponse = requireNotNull(response.body())
                            val data = searchResponse.results.map { SearchResult(it) }
                            continuation.resume(data)
                            return
                        }
                        val exception = IllegalStateException(response.errorBody()?.string())
                        Log.e(LOG_TAG, "Retrofit failure!", exception)
                        continuation.resumeWithException(exception)
                    }

                    override fun onFailure(call: Call<SearchResponse>, exception: Throwable) {
                        if (call.isCanceled) {
                            continuation.cancel()
                            return
                        }
                        Log.e(LOG_TAG, "Retrofit failure!", exception)
                        continuation.resumeWithException(exception)
                    }
                })
        }
    }
}
