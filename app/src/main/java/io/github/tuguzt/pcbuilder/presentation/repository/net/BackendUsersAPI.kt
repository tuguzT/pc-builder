package io.github.tuguzt.pcbuilder.presentation.repository.net

import androidx.annotation.CheckResult
import io.github.tuguzt.pcbuilder.presentation.model.user.UserData
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendUsersAPI {
    @GET("all")
    @CheckResult
    suspend fun getAll(): BackendResponse<List<UserData>>

    @GET("current")
    @CheckResult
    suspend fun current(): BackendResponse<UserData>

    @GET("id/{id}")
    @CheckResult
    suspend fun findById(@Path("id") id: String): BackendResponse<UserData?>

    @GET("username/{username}")
    @CheckResult
    suspend fun findByUsername(@Path("username") username: String): BackendResponse<UserData?>
}
