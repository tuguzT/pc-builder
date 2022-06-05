package io.github.tuguzt.pcbuilder.data.datasource.remote.api

import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
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

    @POST("oauth2/google")
    suspend fun googleOAuth2(@Body authCode: UserTokenData): BackendResponse<UserTokenData>
}
