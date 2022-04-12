package io.github.tuguzt.pcbuilder.presentation.repository.net

import androidx.annotation.CheckResult
import io.github.tuguzt.pcbuilder.presentation.model.user.UserCredentialsData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserNamePasswordData
import io.github.tuguzt.pcbuilder.presentation.model.user.UserTokenData
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendAuthAPI {
    @POST("auth")
    @CheckResult
    suspend fun auth(@Body credentials: UserCredentialsData): BackendResponse<UserTokenData>

    @POST("register")
    @CheckResult
    suspend fun register(@Body user: UserNamePasswordData): BackendResponse<UserTokenData>

    @POST("oauth2/google")
    @CheckResult
    suspend fun googleOAuth2(@Body tokenData: UserTokenData): BackendResponse<UserTokenData>
}
