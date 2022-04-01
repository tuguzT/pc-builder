package io.github.tuguzt.pcbuilder.presentation.repository.net

import io.github.tuguzt.pcbuilder.presentation.model.user.UserData
import retrofit2.http.GET
import retrofit2.http.Path

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
