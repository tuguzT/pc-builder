package io.github.tuguzt.pcbuilder.presentation.repository.net.backend

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.tuguzt.pcbuilder.presentation.repository.net.json
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.create

/**
 * Client of the backend API defined by [BackendAPI].
 */
object BackendClient {
    @JvmStatic
    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pc-builder-tuguzt.herokuapp.com")
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        .build()

    @JvmStatic
    private val backendAPI: BackendAPI = retrofit.create()

    @JvmStatic
    suspend fun getOctopartToken(): String = backendAPI.getOctopartToken().await()
}
