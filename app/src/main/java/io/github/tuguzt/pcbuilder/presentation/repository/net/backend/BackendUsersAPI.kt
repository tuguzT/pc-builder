package io.github.tuguzt.pcbuilder.presentation.repository.net.backend

import io.github.tuguzt.pcbuilder.presentation.model.user.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendUsersAPI {
    @GET
    fun getAll(): Call<List<UserData>>

    @GET("current")
    fun current(): Call<UserData>

    @GET("id/{id}")
    fun findById(@Path("id") id: String): Call<UserData?>

    @GET("username/{username}")
    fun findByUsername(@Path("username") username: String): Call<UserData?>
}
