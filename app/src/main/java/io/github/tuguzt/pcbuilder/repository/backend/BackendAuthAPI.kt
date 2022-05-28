package io.github.tuguzt.pcbuilder.repository.backend

import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Backend API for authentication (requires no access token).
 */
interface BackendAuthAPI {
    @POST("auth")
    suspend fun auth(@Body credentials: UserCredentialsData): BackendResponse<UserTokenData>

    @POST("register")
    suspend fun register(@Body credentials: UserCredentialsData): BackendResponse<UserTokenData>
}
