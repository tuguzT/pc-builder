package io.github.tuguzt.pcbuilder.presentation.repository.net.backend

import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import retrofit2.Call
import retrofit2.http.POST

interface BackendAuthAPI {
    @POST("auth")
    fun auth(user: UserNamePassword): Call<String>

    @POST("register")
    fun register(user: UserNamePassword): Call<String>
}
