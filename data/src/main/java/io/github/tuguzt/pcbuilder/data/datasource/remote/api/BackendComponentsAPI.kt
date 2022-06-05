package io.github.tuguzt.pcbuilder.data.datasource.remote.api

import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Backend API for PC component management (requires access token).
 */
interface BackendComponentsAPI {
    @GET("components/all")
    suspend fun getAll(): BackendResponse<List<ComponentData>>

    @GET("components/id/{id}")
    suspend fun findById(@Path("id") id: String): BackendResponse<ComponentData?>

    @GET("components/name/{name}")
    suspend fun findByName(@Path("name") name: String): BackendResponse<ComponentData?>
}
