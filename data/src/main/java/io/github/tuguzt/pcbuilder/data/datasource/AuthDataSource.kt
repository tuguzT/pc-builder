package io.github.tuguzt.pcbuilder.data.datasource

import io.github.tuguzt.pcbuilder.data.datasource.remote.BackendResponse
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData

interface AuthDataSource {
    suspend fun auth(credentials: UserCredentialsData): BackendResponse<UserTokenData>

    suspend fun register(credentials: UserCredentialsData): BackendResponse<UserTokenData>

    suspend fun googleOAuth2(authCode: UserTokenData): BackendResponse<UserTokenData>
}
