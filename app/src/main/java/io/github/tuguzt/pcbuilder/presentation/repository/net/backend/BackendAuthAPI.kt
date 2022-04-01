package io.github.tuguzt.pcbuilder.presentation.repository.net.backend

import io.github.tuguzt.pcbuilder.presentation.model.user.UserCredentialsData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserNamePasswordData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserTokenData
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendAuthAPI {
    @POST("auth")
    suspend fun auth(@Body credentials: UserCredentialsData): BackendResponse<UserTokenData>

    @POST("register")
    suspend fun register(@Body user: UserNamePasswordData): BackendResponse<UserTokenData>
}
