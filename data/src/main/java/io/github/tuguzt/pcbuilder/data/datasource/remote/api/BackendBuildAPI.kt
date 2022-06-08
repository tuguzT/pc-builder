package io.github.tuguzt.pcbuilder.data.datasource.remote.api

import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendCompletableResponse
import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Backend API for PC builds management (requires access token).
 */
interface BackendBuildAPI {
    @GET("builds/all")
    suspend fun getAll(): BackendResponse<List<BuildData>>

    @GET("builds/id/{id}")
    suspend fun findById(@Path("id") id: NanoId): BackendResponse<BuildData?>

    @GET("builds/name/{name}")
    suspend fun findByName(@Path("name") name: String): BackendResponse<BuildData?>

    @POST("builds/save")
    suspend fun save(@Body build: BuildData): BackendCompletableResponse

    @POST("builds/delete")
    suspend fun delete(@Body build: BuildData): BackendCompletableResponse
}
