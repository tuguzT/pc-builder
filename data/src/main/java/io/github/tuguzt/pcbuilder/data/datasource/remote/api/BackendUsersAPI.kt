package io.github.tuguzt.pcbuilder.data.datasource.remote.api

import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Backend API for user management (requires access token).
 */
interface BackendUsersAPI {
    @GET("all")
    suspend fun getAll(): BackendResponse<List<UserData>>

    @GET("current")
    suspend fun current(): BackendResponse<UserData>

    @GET("id/{id}")
    suspend fun findById(@Path("id") id: String): BackendResponse<UserData?>

    @GET("username/{username}")
    suspend fun findByUsername(@Path("username") username: String): BackendResponse<UserData?>
}
