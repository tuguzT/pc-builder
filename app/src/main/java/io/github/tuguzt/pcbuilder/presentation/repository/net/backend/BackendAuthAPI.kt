package io.github.tuguzt.pcbuilder.presentation.repository.net.backend

import io.github.tuguzt.pcbuilder.presentation.model.user.UserCredentialsData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserNamePasswordData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserTokenData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendAuthAPI {
    @POST("auth")
    fun auth(@Body credentials: UserCredentialsData): Call<UserTokenData>

    @POST("register")
    fun register(@Body user: UserNamePasswordData): Call<UserTokenData>
}
