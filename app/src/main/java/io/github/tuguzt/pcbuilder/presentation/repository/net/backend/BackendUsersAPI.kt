package io.github.tuguzt.pcbuilder.presentation.repository.net.backend

import io.github.tuguzt.pcbuilder.domain.model.user.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendUsersAPI {
    @GET
    fun getAll(): Call<List<User>>

    @GET("id/{id}")
    fun findById(@Path("id") id: String): Call<User?>

    @GET("username/{username}")
    fun findByUsername(@Path("username") username: String): Call<User?>
}
